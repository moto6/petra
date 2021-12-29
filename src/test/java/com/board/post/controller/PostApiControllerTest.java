package com.board.post.controller;

import com.board.post.entity.Post;
import com.board.post.repository.PostRepository;
import com.board.post.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

import static com.board.post.PostDtoRequestTestDataSet.request1;
import static com.board.post.PostDtoRequestTestDataSet.request2;
import static com.board.post.PostDtoRequestTestDataSet.request3;
import static com.board.post.PostDtoRequestTestDataSet.request4;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PostApiControllerTest {

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

    @Autowired
    private PostService postService;

    @PersistenceContext
    EntityManager em;

    @Autowired
    private WebApplicationContext ctx;

    Logger log = LoggerFactory.getLogger(getClass());


    @BeforeEach
    public void setup() {
        postRepository.deleteAll();

        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
                .alwaysDo(print())
                .build();
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
        log.info("Contents 와 Title 은 그대로 반영된다");
        assertThat(afterUpdate.getContents()).isEqualTo(request2.getContents());
        assertThat(afterUpdate.getTitle()).isEqualTo(request2.getTitle());
        log.info("유효기간은 null 인 경우 서버에서 검증된다");
        assertThat(afterUpdate.getValidUntil()).isEqualTo(savedPost.getValidUntil());
        assertThat(afterUpdate.getValidFrom()).isEqualTo(savedPost.getValidFrom());
    }


    @Test
    @Transactional
    @DisplayName("Post 삭제된다")
    public void deletePost() throws Exception {

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
        } catch (EntityNotFoundException e) {
            log.info("savedPost1 삭제 성공");
            count--;
        }

        try {
            postRepository.findById(savedPost2.getId()).orElseThrow(EntityNotFoundException::new);
        } catch (EntityNotFoundException e) {
            log.info("savedPost1 삭제 성공");
            count--;
        }

        assertThat(count).isZero();
    }

    @Test
    @DisplayName("Post 단건조회")
    public void readPost() throws Exception {
        //given
        Post postA = request1.deepCopy().validExtension().toPost();
        Post postB = request2.deepCopy().validExtension().toPost();
        Post postC = request3.deepCopy().validExtension().toPost();


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

    @Test
    @DisplayName("Post 유효기간 지나고 조회시 405/MethodNotAllowed")
    public void readExpired() throws Exception {
        //given
        Post post = (modelMapper.map(request2, Post.class));
        post.config("rolling");
        Post savedPost = postRepository.save(post);

        //when
        //then
        mockMvc.perform(get(PREFIX + SLASH + savedPost.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isMethodNotAllowed());
    }


    @Test
    @DisplayName("Post 단건조회 & 유효기간 무시")
    public void readAny() throws Exception {
        //given
        Post post = (modelMapper.map(request2, Post.class));
        post.config("rolling");
        Post savedPost = postRepository.save(post);

        //when
        //then
        mockMvc.perform(get(PREFIX + SLASH + "any/" + savedPost.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Post 페이지단위 조회")
    public void readPage() throws Exception {
        //given
        Post postA = request1.toPost();
        Post postB = request2.toPost();
        Post postC = request3.toPost();
        Post postD = request4.toPost();

        //when
        Post savedPost1 = postRepository.save(postA);
        Post savedPost2 = postRepository.save(postB);
        Post savedPost3 = postRepository.save(postC);
        Post savedPost4 = postRepository.save(postD);
        List<Post> savePostList = List.of(savedPost1, savedPost2, savedPost3, savedPost4);
        long validPostCount = savePostList.stream().filter(post -> post.isValidPeriod(LocalDateTime.now())).count();

        //then
        mockMvc.perform(get(PREFIX)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());

        Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "id");

        List<?> postValidList = postService.readAll(pageable);
        List<?> postAllList = postService.readAnyAll(pageable);
        assertThat(postValidList.size()).isEqualTo(validPostCount);
        assertThat(postValidList.size()).isNotEqualTo(postAllList.size());
        log.info("\n validPostCount : {}\n postValidListSize : {}\n postAllListSize : {}\n",
                 validPostCount, postValidList.size(), postAllList.size());
    }

    @Test
    @DisplayName("Post 페이지단위 조회 & 유효기간 무시")
    public void readPageAny() throws Exception {
        //given
        Post postA = request1.toPost();
        Post postB = request2.toPost();
        Post postC = request3.toPost();
        Post postD = request4.toPost();

        //when
        Post savedPost1 = postRepository.save(postA);
        Post savedPost2 = postRepository.save(postB);
        Post savedPost3 = postRepository.save(postC);
        Post savedPost4 = postRepository.save(postD);
        List<Post> savePostList = List.of(savedPost1, savedPost2, savedPost3, savedPost4);
        long validPostCount = savePostList.stream().filter(post -> post.isValidPeriod(LocalDateTime.now())).count();

        //then
        mockMvc.perform(get(PREFIX + "/any")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());

        //.andExpect(content().string(savedPost1.getTitle()));
        //assertThat()
        log.info("count : {}", validPostCount);

    }

}
