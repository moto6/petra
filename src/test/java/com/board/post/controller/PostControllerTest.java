package com.board.post.controller;

import com.board.post.entity.Post;
import com.board.post.repository.PostRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.session.PersistentManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import static com.board.post.dto.PostDtoRequestTest.request1;
import static com.board.post.dto.PostDtoRequestTest.request2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    private static final String PREFIX = "/api/v1/post";
    private static final String SLASH = "/";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PostRepository postRepository;

    @PersistenceContext
    EntityManager em;

    @BeforeEach
    public void setup() {
        postRepository.deleteAll();
    }


    @Test
    @DisplayName("글이 작성된다")
    public void posting() throws Exception {
        //given
        String requestBody = objectMapper.writeValueAsString(request1);

        //when
        //then
        mockMvc.perform(post(PREFIX)
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());     // 4
        //.andExpect(content().string( "바디내용을 검증해야함"));
    }

    @Test
    @Transactional
    @DisplayName("글이 수정된다")
    public void update() throws Exception {
        //given
        String requestBody = objectMapper.writeValueAsString(request2);

        //when
        Post savedPost = postRepository.save(modelMapper.map(request1, Post.class));


        //then
        mockMvc.perform(put(PREFIX + SLASH + savedPost.getId())
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());

        em.flush();
        em.clear();
        Post afterUpdate = postRepository.findById(savedPost.getId()).orElseThrow(EntityNotFoundException::new);
        assertThat(afterUpdate.getContents()).isEqualTo(request2.getContents());
        assertThat(afterUpdate.getValidUntil()).isEqualTo(request2.getValidUntil());

    }
}
