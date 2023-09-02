package com.neshan.restaurantmanagement.controller;

import com.neshan.restaurantmanagement.model.ApiResponse;
import com.neshan.restaurantmanagement.model.dto.CommentDto;
import com.neshan.restaurantmanagement.model.dto.CommentRequestDto;
import com.neshan.restaurantmanagement.service.CommentService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RateLimiter(name = "rate-limit")
@AllArgsConstructor
public class CommentController {

    private CommentService commentService;

    @GetMapping("/comments")
    public ResponseEntity<ApiResponse<List<CommentDto>>> getAllComments() {

        var comments = commentService.getAllComments();

        var response = ApiResponse
                .<List<CommentDto>>builder()
                .status("success")
                .data(comments)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<ApiResponse<CommentDto>> getComment(
            @PathVariable long id) {

        var comment = commentService.getComment(id);

        var response = ApiResponse
                .<CommentDto>builder()
                .status("success")
                .data(comment)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/items/{itemId}/comments")
    public ResponseEntity<ApiResponse<List<CommentDto>>> getAllCommentsOfItem(
            @PathVariable Long itemId) {

        var comments = commentService.getAllCommentsOfItem(itemId);

        var response = ApiResponse
                .<List<CommentDto>>builder()
                .status("success")
                .data(comments)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/my-comments")
    public ResponseEntity<ApiResponse<List<CommentDto>>> getAllCommentsOfUser(
            HttpServletRequest request
    ) {

        var comments = commentService.getAllCommentsOfUser(request);

        var response = ApiResponse
                .<List<CommentDto>>builder()
                .status("success")
                .data(comments)
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/items/{itemId}/comments")
    public ResponseEntity<ApiResponse<Object>> addComment(
            @PathVariable long itemId,
            HttpServletRequest request,
            @Valid @RequestBody CommentRequestDto commentRequestDto
    ) {

        commentService.addComment(itemId, request, commentRequestDto);

        var response = ApiResponse
                .builder()
                .status("success")
                .message("Comment was created successfully.")
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/comments/{id}")
    public ResponseEntity<ApiResponse<Object>> addCommentResponse(
            @PathVariable long id,
            @Valid @RequestBody CommentDto commentDto) {

        commentService.addCommentResponse(id, commentDto);

        var response = ApiResponse
                .builder()
                .status("success")
                .message("Comment was updated successfully.")
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/comments")
    public ResponseEntity<ApiResponse<Object>> deleteAllComments() {
        commentService.deleteAllComments();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteComment(@PathVariable long id) {

        commentService.deleteComment(id);

        var response = ApiResponse
                .builder()
                .status("success")
                .message("Comment was deleted successfully.")
                .build();

        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
