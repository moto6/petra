package com.board.post.controller;

import com.board.post.entity.Post;
import com.board.post.repository.PostRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.session.PersistentManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import static com.board.post.dto.PostDtoRequestTest.request3;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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

    Logger log = LoggerFactory.getLogger(getClass());

    @BeforeEach
    public void setup() {
        postRepository.deleteAll();
    }


    @Test
    @DisplayName("Post 생성된다")
    public void postingPost() throws Exception {
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
    @DisplayName("Post 수정된다")
    public void updatePost() throws Exception {
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


    @Test
    @Transactional
    @DisplayName("Post 삭제된다")
    public void deletePost() throws Exception{

        //given
        int count = 2;
        Post savedPost1 = postRepository.save(modelMapper.map(request1, Post.class));
        Post savedPost2 = postRepository.save(modelMapper.map(request2, Post.class));

        //when
        mockMvc.perform(delete(PREFIX + SLASH + savedPost1.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(delete(PREFIX + SLASH + savedPost2.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());

        //then
        em.flush();
        em.clear();

        try {
            postRepository.findById(savedPost1.getId()).orElseThrow(EntityNotFoundException::new);
        }catch (EntityNotFoundException e) {
            log.info("savedPost1 삭제 성공");
            count--;
        }

        try {
            postRepository.findById(savedPost2.getId()).orElseThrow(EntityNotFoundException::new);
        }catch (EntityNotFoundException e) {
            log.info("savedPost1 삭제 성공");
            count--;
        }

        assertThat(count).isZero();
    }

    @Test
    @DisplayName("Post 단건조회")
    public void readPost() throws Exception {
        //given
        Post postA = modelMapper.map(request1.validExtension(), Post.class);
        Post postB = modelMapper.map(request2.validExtension(), Post.class);
        Post postC = modelMapper.map(request3.validExtension(), Post.class);


        //when
        Post savedPost1 = postRepository.save(postA);
        Post savedPost2 = postRepository.save(postB);
        Post savedPost3 = postRepository.save(postC);

        //then
        mockMvc.perform(get(PREFIX + SLASH + savedPost1.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
        //.andExpect(content().string(savedPost1.getTitle()));


        mockMvc.perform(get(PREFIX + SLASH + savedPost2.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
        //.andExpect(content().string(savedPost2.getTitle()));


        mockMvc.perform(get(PREFIX + SLASH + savedPost3.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
        //.andExpect(content().string(savedPost3.getTitle()));

    }

    }
}
