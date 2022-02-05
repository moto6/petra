package com.carrot.comment.controller;

import com.carrot.account.domain.Account;
import com.carrot.auth.AuthUser;
import com.carrot.comment.dto.CommentRequestDto;
import com.carrot.comment.dto.CommentResponseDto;
import com.carrot.comment.domain.Comment;
import com.carrot.comment.service.CommentService;
import com.carrot.common.apiresult.ApiResult;
import lombok.RequiredArgsConstructor;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.carrot.comment.dto.CommentResponseDto.commentListResponse;
import static com.carrot.common.apiresult.ApiResult.success;

@RequestMapping("api/v1/comment")
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping("article/{articleId}")
    public ResponseEntity<?> create(@RequestBody CommentRequestDto request, @PathVariable Long articleId, @AuthUser Account account) {

        Comment comment = commentService.create(articleId, account, request.createComment());
        ApiResult<?> result = success(new CommentResponseDto(comment), HttpStatus.CREATED);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(result);
    }

    @DeleteMapping("{commentId}/article/{articleId}")
    public ResponseEntity<?> delete(
            @PathVariable Long articleId,
            @PathVariable Long commentId,
            @AuthUser Account account) {

        commentService.delete(articleId, commentId, account);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<?> read(@PathVariable Long commentId) {

        Comment comment = commentService.read(commentId);
        ApiResult<?> result = success(new CommentResponseDto(comment), HttpStatus.OK);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    @GetMapping("article/{articleId}")
    public ResponseEntity<?> readComments(
            @PageableDefault(size = 20, sort = "createAt", direction = Sort.Direction.ASC) Pageable pageable,
            @PathVariable Long articleId) {

        Page<Comment> commentList = commentService.readCommentsList(articleId, pageable);
        ApiResult<?> results = success(commentListResponse(commentList), HttpStatus.OK);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(results);
    }

}
