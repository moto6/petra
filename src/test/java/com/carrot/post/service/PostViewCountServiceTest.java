package com.carrot.post.service;

import com.carrot.post.entity.Post;
import com.carrot.post.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.carrot.post.dto.PostDtoRequestTest.request1;

@Slf4j
@SpringBootTest
class PostViewCountServiceTest {

    @Autowired
    PostRepository postRepository;

    //@Autowired
    //PostViewCountService postViewCountService;

    @Test
    @Disabled
    @DisplayName("조회수 증가는 1초 지연 반영")
    public void viewUpCount() {

        //given
        Post post = postRepository.save(request1.toPost());
        final long postId = post.getId();

        /*
        //when
        postViewCountService.intervalCount(postId);// 선발신호 여기서부터 1초 이내에는 조회수 증가 업데이트 쿼리가 나가지 않습니다
        Post nothingChange = postRepository.findById(post.getId()).orElseThrow(EntityExistsException::new);
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

        try {
            sleep(2000);
        } catch (InterruptedException e) {
            log.error("테스트 도중 중간에 인터럽트가 발생해 실패합니다");
            fail();
        }

        //then
        postViewCountService.intervalCount(postId);
        Post postAfter = postRepository.findById(post.getId()).orElseThrow(EntityExistsException::new);


        assertThat(nothingChange.getViewCount()).isEqualTo(postBefore.getViewCount());
        log.info("정상적으로 캐싱이 되는경우 nothingChange 와 postBefore 는 동일한 숫자가 나와야 합니다 : {}/{}", nothingChange.getViewCount(), postBefore.getViewCount());
        assertThat(postAfter.getViewCount()).isNotEqualTo(postBefore.getViewCount());
         */

        //log.info("정상적으로 캐싱이 되는경우 postAfter 와 postBefore 는 서로 다른 숫자가 나옵니다 : {}/{}", postAfter.getViewCount(), postBefore.getViewCount());

        log.info("지금 코드는 실제 조회수와 항상 1 차이가 나는 버그가 있는데, 나중에 Redis Key expire event 를 수신하는 방식으로 구현하면 >> 조회수가 항상 1이상 차이나는 해당 버그를 해결할 수 있습니다");
    }
}
