package com.carrot.article.controller;

import com.carrot.common.ApiResult;
import com.carrot.article.dto.ArticleDtoRequest;
import com.carrot.article.dto.ArticleDtoResponse;
import com.carrot.article.dto.ArticleListDtoResponse;
import com.carrot.article.entity.Article;
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

@RequestMapping("/api/v1/article")
@RestController
@RequiredArgsConstructor
public class ArticleApiController {

    private final ArticleService articleService;

    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<?> createArticle(@RequestBody ArticleDtoRequest request) {
        ArticleDtoResponse response = modelMapper.map(articleService.save(request.toEntity()), ArticleDtoResponse.class);
        ApiResult<?> result = ApiResult.sussess(response, HttpStatus.CREATED);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(result);
    }

    @PutMapping("/{articleId}")
    public ResponseEntity<?> updateArticle(@PathVariable Long articleId, @RequestBody ArticleDtoRequest request) {

        ArticleDtoResponse response = modelMapper.map(articleService.update(articleId, request.toEntity()), ArticleDtoResponse.class);
        ApiResult<?> result = ApiResult.sussess(response, HttpStatus.OK);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    @DeleteMapping("/{articleId}")
    public ResponseEntity<?> deleteArticle(@PathVariable Long articleId) {

        articleService.delete(articleId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<?> getArticle(@PathVariable Long articleId, @RequestParam(required = false, defaultValue = "") String query) {

        SearchType search = SearchTypeAdaptor(query);
        ArticleDtoResponse response = modelMapper.map(articleService.get(articleId, search), ArticleDtoResponse.class);
        ApiResult<?> result = ApiResult.sussess(response, HttpStatus.OK);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    @GetMapping
    public ResponseEntity<?> getArticlePage(
            @PageableDefault(direction = Sort.Direction.ASC) Pageable pageable) {

        List<Article> articleList = articleService.getPage(pageable);
        List<ArticleDtoResponse> responseList
                = articleList.stream()
                .map(article -> modelMapper.map(article, ArticleDtoResponse.class))
                .collect(Collectors.toList());

        ArticleListDtoResponse response = new ArticleListDtoResponse(responseList, pageable);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/every")
    public ResponseEntity<?> readPageEvery(
            @PageableDefault(direction = Sort.Direction.ASC) Pageable pageable) {

        List<Article> articleList = articleService.getPageEvery(pageable);
        List<ArticleDtoResponse> responseList
                = articleList.stream()
                .map(article -> modelMapper.map(article, ArticleDtoResponse.class))
                .collect(Collectors.toList());

        ArticleListDtoResponse response = new ArticleListDtoResponse(responseList, pageable);
        return ResponseEntity.ok(response);
    }
}
