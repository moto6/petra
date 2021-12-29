package com.board.attachfile.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AttachFileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    //@Disabled
    @Test
    @DisplayName("uploadFile 메서드 검증")
    public void uploadFile() throws Exception {

        //given
        MockMultipartFile file = new MockMultipartFile("file", "test.json", "application/json", new byte[]{1, 2, 3, 4});

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/file")
                                .file(file)
                                .characterEncoding("UTF-8"))
                .andExpect(status().is3xxRedirection());


    }


    @Test
    @DisplayName("uploadFileHandler 메서드 검증")
    public void uploadFileHandler() throws Exception {

        //given
        MockMultipartFile jsonFile = new MockMultipartFile("test.json", "test.json", "application/json", "{\"key1\": \"value1\"}".getBytes());

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.multipart("/upload")
                                .file("file", jsonFile.getBytes())
                                .characterEncoding("UTF-8"))
                .andExpect(status().isOk());
    }
}
