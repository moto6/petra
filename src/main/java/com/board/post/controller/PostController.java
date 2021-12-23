package com.board.post.controller;

import com.board.post.entity.Post;
import com.board.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping()
    public Post hi () {
        return new Post(11L,"hello world");
    }

}
