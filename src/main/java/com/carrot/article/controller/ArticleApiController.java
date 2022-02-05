package com.carrot.article.controller;

import com.carrot.common.ApiResult;
import com.carrot.article.dto.ArticleDtoRequest;
import com.carrot.article.dto.ArticleDtoResponse;
import com.carrot.article.dto.ArticleListDtoResponse;
import com.carrot.article.entity.Post;
import com.carrot.article.service.ArticleService;
import com.carrot.article.util.SearchType;
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

import static com.carrot.article.util.SearchType.SearchTypeAdaptor;

@RequestMapping("/api/v1/post")
@RestController
@RequiredArgsConstructor
public class ArticleApiController {

    private final ArticleService articleService;

    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody ArticleDtoRequest request) {
        ArticleDtoResponse response = modelMapper.map(articleService.save(request.toPost()), ArticleDtoResponse.class);
        ApiResult<?> result = ApiResult.sussess(response, HttpStatus.CREATED);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(result);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable Long postId, @RequestBody ArticleDtoRequest request) {
        ArticleDtoResponse response = modelMapper.map(articleService.update(postId, request.toPost()), ArticleDtoResponse.class);
        ApiResult<?> result = ApiResult.sussess(response, HttpStatus.OK);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        articleService.delete(postId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> getPost(@PathVariable Long postId, @RequestParam(required = false, defaultValue = "") String query) {
        SearchType search = SearchTypeAdaptor(query);
        ArticleDtoResponse response = modelMapper.map(articleService.get(postId, search), ArticleDtoResponse.class);
        ApiResult<?> result = ApiResult.sussess(response, HttpStatus.OK);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);

    }

    @GetMapping
    public ResponseEntity<?> getPostPage(
            @PageableDefault(direction = Sort.Direction.ASC) Pageable pageable) {

        List<Post> postList = articleService.getPage(pageable);
        List<ArticleDtoResponse> dtoList = postList
                .stream()
                .map(post -> modelMapper.map(post, ArticleDtoResponse.class))
                .collect(Collectors.toList());

        ArticleListDtoResponse dto = new ArticleListDtoResponse(dtoList, pageable);
        return ResponseEntity.ok(dto);
    }


    @GetMapping("/every")
    public ResponseEntity<?> readPageEvery(
            @PageableDefault(direction = Sort.Direction.ASC) Pageable pageable) {

        List<Post> postList = articleService.getPageEvery(pageable);
        List<ArticleDtoResponse> dtoList = postList
                .stream()
                .map(post -> modelMapper.map(post, ArticleDtoResponse.class))
                .collect(Collectors.toList());

        ArticleListDtoResponse dto = new ArticleListDtoResponse(dtoList, pageable);
        return ResponseEntity.ok(dto);
    }

}
