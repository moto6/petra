package com.board.post.controller;

import com.board.post.dto.PostDtoRequest;
import com.board.post.entity.Post;
import com.board.post.repository.PostRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.board.post.dto.PostDtoRequestTest.request1;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Disabled
    @Test
    @DisplayName("uploadFile 검증")
    public void uploadFile() throws Exception{

        //given
        MockMultipartFile jsonFile = new MockMultipartFile("test.json", "", "application/json", "{\"key1\": \"value1\"}".getBytes());
        PostDtoRequest request = request1;
        List<MultipartFile> files = createMultipartFiles();

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.multipart("/fileUpload")
                                .file("file", jsonFile.getBytes())
                                .content("need content")
                                .characterEncoding("UTF-8"))
                .andExpect(status().isOk());
    }

    private List<MultipartFile> createMultipartFiles() throws Exception {

        List<MultipartFile> multipartFileList = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            String path = "/";
            String imageName = "image" + i + ".jpg";
            MockMultipartFile multipartFile =
                    new MockMultipartFile(path, imageName, "file", new byte[]{1, 2, 3, 4});
            multipartFileList.add(multipartFile);
        }

        return multipartFileList;
    }

}
