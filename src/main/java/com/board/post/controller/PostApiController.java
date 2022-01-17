package com.board.post.controller;

import com.board.post.dto.PostDtoRequest;
import com.board.post.dto.PostDtoResponse;
import com.board.post.dto.PostListDtoResponse;
import com.board.post.entity.Post;
import com.board.post.service.PostService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/v1/post")
@RestController
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;

    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody PostDtoRequest request) {
        Long id = postService.save(request);
        return ResponseEntity.ok(id); //@create 로 변경
    }

    @PutMapping("/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable Long postId, @RequestBody PostDtoRequest request) {
        Long id = postService.update(postId, request);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        postService.delete(postId);
        return ResponseEntity.ok("delete");
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> getPost(@PathVariable Long postId, @RequestParam String query) {

        if (query.equals("any")) {
            PostDtoResponse response = modelMapper.map(postService.getAny(postId), PostDtoResponse.class);
            return ResponseEntity.ok(response);
        }

        PostDtoResponse response = modelMapper.map(postService.get(postId), PostDtoResponse.class);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> getPostPage(
            @PageableDefault(direction = Sort.Direction.ASC) Pageable pageable) {

        List<Post> postList = postService.getPage(pageable);
        List<PostDtoResponse> dtoList = postList
                .stream()
                .map(post -> modelMapper.map(post, PostDtoResponse.class))
                .collect(Collectors.toList());

        PostListDtoResponse dto = new PostListDtoResponse(dtoList, pageable);
        return ResponseEntity.ok(dto);
    }


    /*
    @GetMapping("/any/{postId}")
    public ResponseEntity<?> readAny(@PathVariable Long postId) {

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

     */

}
