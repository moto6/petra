package com.board.post.controller;

import com.board.post.dto.PostDtoResponse;
import com.board.post.entity.Post;
import com.board.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;

import java.net.URI;

@RequestMapping("/api/v1/post")
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<?> create () {
        return ResponseEntity.ok("create"); //@create 로 변경
    }

    @PutMapping("/{postId}")
    public ResponseEntity<?> update (@PathVariable Long postId) {
        return ResponseEntity.ok("update");
        }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> delete (@PathVariable Long postId) {
        return ResponseEntity.ok("delete");
        }

    @GetMapping("/{postId}")
    public ResponseEntity<?> read (@PathVariable Long postId) {
        return ResponseEntity.ok(new PostDtoResponse());
    }

    @GetMapping
    public ResponseEntity<?> readPage () {
        return ResponseEntity.ok("readPage");
    }

    //@todo : 페이지로 목록조회

}
