package com.board.post.service;

import com.board.post.entity.Post;
import com.board.post.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.board.post.entity.PostTest.post1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    @Test
    @DisplayName("조회수가 증가한다")
    public void addViews() {
        //given
        Post post = post1.deepCopy();
        long initialCount = post.getViewCount();
        long increase = 12345;
        when(postRepository.findById(any())).thenReturn(Optional.of(post));

        //when
        postService.addViews(1L, increase);

        //then
        assertThat(initialCount + increase).isEqualTo(post.getViewCount());
    }
}
