package org.example.expert.domain.comment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.expert.domain.comment.dto.request.CommentSaveRequest;
import org.example.expert.domain.comment.dto.response.CommentResponse;
import org.example.expert.domain.comment.dto.response.CommentSaveResponse;
import org.example.expert.domain.comment.service.CommentService;
import org.example.expert.domain.common.annotation.Auth;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.user.enums.UserRole;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/todos/{todoId}/comments")
    public ResponseEntity<CommentSaveResponse> saveComment(
            @Auth AuthUser authUser,
            @PathVariable("todoId") Long todoId,
            @Valid @RequestBody CommentSaveRequest commentSaveRequest
    ) {
        return ResponseEntity.ok(commentService.saveComment(authUser, todoId, commentSaveRequest));
    }

    @GetMapping("/todos/{todoId}/comments")
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable("todoId") long todoId) {
        return ResponseEntity.ok(commentService.getComments(todoId));
    }
}
