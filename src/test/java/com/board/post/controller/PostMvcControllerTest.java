package com.board.post.controller;

import com.board.post.entity.Post;
import com.board.post.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static com.board.post.dto.PostDtoRequestTest.request1;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PostMvcControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Test
    @DisplayName("글 작성 페이지가 로딩된다")
    public void formLoading() throws Exception {

        //given
        final String URI = "/post";

        //when
        //then
        mockMvc.perform(get(URI)).andExpect(status().isOk());
    }


    @Test
    @DisplayName("글 상세내용 페이지가 로딩된다")
    public void form() throws Exception {

        //given
        Post post = postRepository.save(request1.toPost());
        String URI = "/post/" + post.getId();

        //when
        //then
        mockMvc.perform(get(URI)).andExpect(status().isOk());
    }

}
