package com.board.post.controller;

import com.board.post.dto.PostDtoRequest;
import com.board.post.dto.PostDtoResponse;
import com.board.post.dto.PostListDtoResponse;
import com.board.post.entity.Post;
import com.board.post.service.PostService;
import com.board.post.service.PostViewCountService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/v1/post")
@RestController
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;

    private final ModelMapper modelMapper;

    private final PostViewCountService postViewCountService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody PostDtoRequest request) {
        Long id = postService.save(request);
        return ResponseEntity.ok(id); //@create 로 변경
    }

    @PutMapping("/{postId}")
    public ResponseEntity<?> update(@PathVariable Long postId, @RequestBody PostDtoRequest request) {
        Long id = postService.update(postId, request);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> delete(@PathVariable Long postId) {
        postService.delete(postId);
        return ResponseEntity.ok("delete");
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> readSingle(@PathVariable Long postId) {
        Post post = postService.read(postId);
        postViewCountService.intervalCount(postId);//@ todo : 조회수 카운트를 redis 에서 처리
        PostDtoResponse response = modelMapper.map(post, PostDtoResponse.class);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> readPage(
            @PageableDefault(direction = Sort.Direction.ASC) Pageable pageable) {

        List<Post> postList = postService.readAll(pageable);
        List<PostDtoResponse> dtoList = postList
                .stream()
                .map(post -> modelMapper.map(post, PostDtoResponse.class))
                .collect(Collectors.toList());

        PostListDtoResponse dto = new PostListDtoResponse(dtoList, pageable);
        return ResponseEntity.ok(dto);
    }

    //YAGNI 에 의해 요청이 요구사항에는 없지만, 해당 과제전형의 중요 평가 요소가 유효기간 기능이라고 생각해 미리 작성
    @GetMapping("/any/{postId}")
    public ResponseEntity<?> readAny(@PathVariable Long postId) {
        Post post = postService.readAny(postId);
        PostDtoResponse response = modelMapper.map(post, PostDtoResponse.class);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/any")
    public ResponseEntity<?> readPageAny(
            @PageableDefault(direction = Sort.Direction.ASC) Pageable pageable) {

        List<Post> postList = postService.readAnyAll(pageable);
        List<PostDtoResponse> dtoList = postList
                .stream()
                .map(post -> modelMapper.map(post, PostDtoResponse.class))
                .collect(Collectors.toList());

        PostListDtoResponse dto = new PostListDtoResponse(dtoList, pageable);
        return ResponseEntity.ok(dto);
    }

}