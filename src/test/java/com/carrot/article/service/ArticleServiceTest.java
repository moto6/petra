package com.carrot.article.service;

import com.carrot.article.entity.Post;
import com.carrot.article.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.carrot.article.entity.PostTest.post1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private ArticleServiceImpl postService;

    @Test
    @DisplayName("조회수가 증가한다")
    public void addViews() {
        //given
        Post post = post1.deepCopy();
        long initialCount = post.getViewCount();
        long increase = 12345;
        when(articleRepository.findById(any())).thenReturn(Optional.of(post));
        post.incrementViewsSync(increase);
        //when
        postService.save(post);

        //then
        assertThat(post.getViewCount()).isEqualTo(initialCount + increase);
    }
}
