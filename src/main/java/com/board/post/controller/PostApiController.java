package com.board.post.controller;

import com.board.common.ApiResult;
import com.board.post.dto.PostDtoRequest;
import com.board.post.dto.PostDtoResponse;
import com.board.post.dto.PostListDtoResponse;
import com.board.post.entity.Post;
import com.board.post.service.PostService;
import com.board.post.util.SearchType;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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

import static com.board.post.util.SearchType.SearchTypeAdaptor;

@RequestMapping("/api/v1/post")
@RestController
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;

    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody PostDtoRequest request) {
        PostDtoResponse response = modelMapper.map(postService.save(request), PostDtoResponse.class);
        ApiResult<?> result = ApiResult.sussess(response, HttpStatus.CREATED);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(result);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable Long postId, @RequestBody PostDtoRequest request) {
        PostDtoResponse response = modelMapper.map(postService.update(postId, request), PostDtoResponse.class);
        ApiResult<?> result = ApiResult.sussess(response, HttpStatus.OK);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        postService.delete(postId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> getPost(@PathVariable Long postId, @RequestParam(required = false) String query) {
        SearchType search = SearchTypeAdaptor(query);
        PostDtoResponse response = modelMapper.map(postService.get(postId,search), PostDtoResponse.class);
        ApiResult<?> result = ApiResult.sussess(response, HttpStatus.OK);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);

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



    @GetMapping("/every")
    public ResponseEntity<?> readPageEvery(
            @PageableDefault(direction = Sort.Direction.ASC) Pageable pageable) {

        List<Post> postList = postService.getPageEvery(pageable);
        List<PostDtoResponse> dtoList = postList
                .stream()
                .map(post -> modelMapper.map(post, PostDtoResponse.class))
                .collect(Collectors.toList());

        PostListDtoResponse dto = new PostListDtoResponse(dtoList, pageable);
        return ResponseEntity.ok(dto);
    }

}
