package com.board.post.service;

import com.board.post.entity.Post;
import com.board.post.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityExistsException;

import static com.board.post.PostDtoRequestTestDataSet.request1;
import static java.lang.Thread.sleep;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Slf4j
@SpringBootTest
class PostViewCountServiceTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostViewCountService postViewCountService;

    @Test
    @DisplayName(" ")
    public void test() {
        //given
        Post post = postRepository.save(request1.toPost());
        final long postId = post.getId();
        //when
        postViewCountService.intervalCount(postId);

        Post beforeincrease = postRepository.findById(post.getId()).orElseThrow(EntityExistsException::new);

        log.info("NONE : getViewCount : {}", beforeincrease.getViewCount());

        postViewCountService.intervalCount(postId);
        postViewCountService.intervalCount(postId);
        postViewCountService.intervalCount(postId);
        postViewCountService.intervalCount(postId);
        postViewCountService.intervalCount(postId);
        postViewCountService.intervalCount(postId);
        postViewCountService.intervalCount(postId);
        postViewCountService.intervalCount(postId);
        postViewCountService.intervalCount(postId);
        postViewCountService.intervalCount(postId);
        postViewCountService.intervalCount(postId);
        postViewCountService.intervalCount(postId);


        Post postBefore = postRepository.findById(post.getId()).orElseThrow(EntityExistsException::new);
        log.info("BEFORE : getViewCount : {}", postBefore.getViewCount());
        try {
            sleep(3000);
        }catch (InterruptedException e) {
            log.error("테스트 도중 중간에 인터럽트가 발생해 실패합니다");
            fail();
        }


        //then
        postViewCountService.intervalCount(postId);
        Post postAfter = postRepository.findById(post.getId()).orElseThrow(EntityExistsException::new);
        log.info("AFTER : getViewCount : {}", postAfter.getViewCount());

        //assertThat(beforeincrease.getViewCount()).isNotEqualTo()

    }
}
