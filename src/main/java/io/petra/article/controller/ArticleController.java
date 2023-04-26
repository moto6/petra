package io.petra.article.controller;

import io.petra.article.dto.ArticleDtoRequest;
import io.petra.article.dto.ArticleDtoResponse;
import io.petra.article.domain.Article;
import io.petra.article.service.ArticleService;
import io.petra.common.response.ApiResult;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
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

import static io.petra.article.util.SearchType.SearchTypeAdaptor;

@RequestMapping("/api/v1/article")
@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<?> createArticle(@RequestBody ArticleDtoRequest request) {

        ArticleDtoResponse response = modelMapper.map(
                articleService.save(request.toEntity()),
                ArticleDtoResponse.class);
        return ApiResult.success(response, HttpStatus.CREATED).responseBuild();
    }

    @PutMapping("/{articleId}")
    public ResponseEntity<?> updateArticle(@PathVariable Long articleId, @RequestBody ArticleDtoRequest request) {

        ArticleDtoResponse response = modelMapper.map(
                articleService.update(articleId, request.toEntity()),
                ArticleDtoResponse.class);
        return ApiResult.success(response, HttpStatus.OK).responseBuild();
    }

    @DeleteMapping("/{articleId}")
    public ResponseEntity<?> deleteArticle(@PathVariable Long articleId) {

        articleService.delete(articleId);
        return ApiResult.success(null, HttpStatus.NO_CONTENT).responseBuild();
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<?> getArticle(@PathVariable Long articleId, @RequestParam(required = false, defaultValue = "") String query) {

        ArticleDtoResponse response = modelMapper.map(
                SearchTypeAdaptor(query).isEmpty() ?
                        articleService.get(articleId)
                        : articleService.query(articleId, SearchTypeAdaptor(query).get()), ArticleDtoResponse.class);
        return ApiResult.success(response, HttpStatus.OK).responseBuild();
    }

    @GetMapping
    public ResponseEntity<?> getArticlePage(
            @PageableDefault(direction = Sort.Direction.ASC) Pageable pageable) {

        Page<Article> articleList = articleService.getPage(pageable);
        Page<ArticleDtoResponse> responseList = ArticleDtoResponse.toResponseList(articleList);
        return ApiResult.success(responseList, HttpStatus.OK).responseBuild();
    }


    @GetMapping("/every")
    public ResponseEntity<?> readPageEvery(
            @PageableDefault(direction = Sort.Direction.ASC) Pageable pageable) {

        Page<Article> articleList = articleService.getPageEvery(pageable);
        Page<ArticleDtoResponse> responseList = ArticleDtoResponse.toResponseList(articleList);
        return ApiResult.success(responseList, HttpStatus.OK).responseBuild();
    }
}
