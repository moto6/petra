package com.carrot.article.controller;

import com.carrot.article.domain.Article;
import com.carrot.article.repository.ArticleRepository;
import com.carrot.article.service.ArticleService;
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
import org.springframework.security.test.context.support.WithMockUser;
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

import static com.carrot.article.dto.ArticleDtoRequestTest.request1;
import static com.carrot.article.dto.ArticleDtoRequestTest.request2;
import static com.carrot.article.dto.ArticleDtoRequestTest.request3;
import static com.carrot.article.dto.ArticleDtoRequestTest.request4;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ArticleApiControllerTest {

    private static final String PREFIX = "/api/v1/article";
    private static final String SLASH = "/";

    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    MockMvc mockMvc;

    @PersistenceContext
    EntityManager em;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private WebApplicationContext ctx;

    @BeforeEach
    public void setup() {
        articleRepository.deleteAll();

        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
                .alwaysDo(print())
                .build();
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("article 생성된다")
    public void postingarticle() throws Exception {
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
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "USER")
    @Transactional
    @DisplayName("article 수정된다")
    public void updatearticle() throws Exception {
        //given
        String requestBody = objectMapper.writeValueAsString(request2);

        //when
        Article savedArticle = articleRepository.save(modelMapper.map(request1, Article.class));


        //then
        mockMvc.perform(put(PREFIX + SLASH + savedArticle.getId())
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());

        em.flush();
        em.clear();
        Article afterUpdate = articleRepository.findById(savedArticle.getId()).orElseThrow(EntityNotFoundException::new);
        log.info("Contents 와 Title 은 그대로 반영된다");
        assertThat(afterUpdate.getContents()).isEqualTo(request2.getContents());
        assertThat(afterUpdate.getTitle()).isEqualTo(request2.getTitle());
        log.info("유효기간은 null 인 경우 서버에서 검증된다");
        assertThat(afterUpdate.getValidUntil()).isEqualTo(savedArticle.getValidUntil());
        assertThat(afterUpdate.getValidFrom()).isEqualTo(savedArticle.getValidFrom());
    }


    @Test
    @WithMockUser(roles = "USER")
    @Transactional
    @DisplayName("article 삭제된다")
    public void deletearticle() throws Exception {

        //given
        Article savedArticle1 = articleRepository.save(modelMapper.map(request1, Article.class));
        Article savedArticle2 = articleRepository.save(modelMapper.map(request2, Article.class));

        //when
        mockMvc.perform(delete(PREFIX + SLASH + savedArticle1.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNoContent());

        mockMvc.perform(delete(PREFIX + SLASH + savedArticle2.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNoContent());

        //then
        em.flush();
        em.clear();

        try {
            articleRepository.findById(savedArticle1.getId()).orElseThrow(EntityNotFoundException::new);
            articleRepository.findById(savedArticle2.getId()).orElseThrow(EntityNotFoundException::new);

        } catch (EntityNotFoundException e) {
            log.info("article 삭제 성공");
        }
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("article 단건조회")
    public void readarticle() throws Exception {
        //given
        Article articleA = request1.deepCopy().validExtension().toEntity();
        Article articleB = request2.deepCopy().validExtension().toEntity();
        Article articleC = request3.deepCopy().validExtension().toEntity();


        //when
        Article savedArticle1 = articleRepository.save(articleA);
        Article savedArticle2 = articleRepository.save(articleB);
        Article savedArticle3 = articleRepository.save(articleC);

        //then
        mockMvc.perform(get(PREFIX + SLASH + savedArticle1.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
        //.andExpect(content().string(savedPost1.getTitle()));


        mockMvc.perform(get(PREFIX + SLASH + savedArticle2.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
        //.andExpect(content().string(savedPost2.getTitle()));


        mockMvc.perform(get(PREFIX + SLASH + savedArticle3.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
        //.andExpect(content().string(savedPost3.getTitle()));

    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("article 유효기간 지나고 조회시 204-NoContent")
    public void readExpired() throws Exception {
        //given
        Article article = (modelMapper.map(request2, Article.class));
        article.config("rolling");
        Article savedArticle = articleRepository.save(article);

        //when
        //then
        mockMvc.perform(get(PREFIX + SLASH + savedArticle.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }


    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("article 단건조회 & 유효기간 무시")
    public void readAny() throws Exception {
        //given
        Article article = (modelMapper.map(request2, Article.class));
        article.config("rolling");
        Article savedArticle = articleRepository.save(article);

        //when
        //then
        mockMvc.perform(get(PREFIX + SLASH + "any/" + savedArticle.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("article 페이지단위 조회")
    public void readPage() throws Exception {
        //given
        Article articleA = request1.toEntity();
        Article articleB = request2.toEntity();
        Article articleC = request3.toEntity();
        Article articleD = request4.toEntity();

        //when
        Article savedArticle1 = articleRepository.save(articleA);
        Article savedArticle2 = articleRepository.save(articleB);
        Article savedArticle3 = articleRepository.save(articleC);
        Article savedArticle4 = articleRepository.save(articleD);
        List<Article> saveArticleList = List.of(savedArticle1, savedArticle2, savedArticle3, savedArticle4);
        long validarticleCount = saveArticleList.stream().filter(article -> article.isExpired(LocalDateTime.now())).count();

        //then
        mockMvc.perform(get(PREFIX)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());

        Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "id");

        List<?> articleValidList = articleService.getPage(pageable).toList();
        List<?> articleAllList = articleService.getPageEvery(pageable).toList();
        assertThat(articleValidList.size()).isEqualTo(validarticleCount);
        assertThat(articleValidList.size()).isNotEqualTo(articleAllList.size());
        log.info("\n validarticleCount : {}\n articleValidListSize : {}\n articleAllListSize : {}\n",
                 validarticleCount, articleValidList.size(), articleAllList.size());
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("article 페이지단위 조회 & 유효기간 무시")
    public void readPageAny() throws Exception {
        //given
        Article articleA = request1.toEntity();
        Article articleB = request2.toEntity();
        Article articleC = request3.toEntity();
        Article articleD = request4.toEntity();

        //when
        Article savedArticle1 = articleRepository.save(articleA);
        Article savedArticle2 = articleRepository.save(articleB);
        Article savedArticle3 = articleRepository.save(articleC);
        Article savedArticle4 = articleRepository.save(articleD);
        List<Article> saveArticleList = List.of(savedArticle1, savedArticle2, savedArticle3, savedArticle4);
        long validarticleCount = saveArticleList.stream().filter(article -> article.isExpired(LocalDateTime.now())).count();

        //then
        mockMvc.perform(get(PREFIX)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());

        //.andExpect(content().string(savedarticle1.getTitle()));
        //assertThat()
        log.info("count : {}", validarticleCount);

    }

}
