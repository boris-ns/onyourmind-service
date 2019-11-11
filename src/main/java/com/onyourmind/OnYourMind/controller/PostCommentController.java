package com.onyourmind.OnYourMind.controller;

import com.onyourmind.OnYourMind.dto.PostCommentDTO;
import com.onyourmind.OnYourMind.mappers.PostCommentMapper;
import com.onyourmind.OnYourMind.model.PostComment;
import com.onyourmind.OnYourMind.service.impl.PostCommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class PostCommentController {

    @Autowired
    private PostCommentServiceImpl commentsService;


    @GetMapping("/public/{id}")
    public ResponseEntity<PostCommentDTO> getComment(@PathVariable Long id) {
        PostComment comment = commentsService.findById(id);
        return new ResponseEntity<>(PostCommentMapper.toDto(comment), HttpStatus.OK);
    }

    @GetMapping("/public")
    public ResponseEntity<List<PostCommentDTO>> getAllComments() {
        List<PostComment> comments = commentsService.findAll();
        return new ResponseEntity<>(PostCommentMapper.toListDto(comments), HttpStatus.OK);
    }

    @GetMapping("/public/user/{id}")
    public ResponseEntity<List<PostCommentDTO>> getCommentsFromUser(@PathVariable Long id) {
        List<PostComment> comments = commentsService.findAllCommentsFromUser(id);
        return new ResponseEntity<>(PostCommentMapper.toListDto(comments), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PostCommentDTO> addComment(@RequestBody PostCommentDTO comment) {
        PostComment newComment = commentsService.addComment(comment);
        return new ResponseEntity<>(PostCommentMapper.toDto(newComment), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public void deleteComment(@PathVariable Long id) {
        commentsService.deleteComment(id);
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<PostCommentDTO> editComment(@RequestBody PostCommentDTO comment) {
        PostComment newComment = commentsService.editComment(comment);
        return new ResponseEntity<>(PostCommentMapper.toDto(newComment), HttpStatus.OK);
    }

    @PutMapping("/deactivate/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deactivateComment(@PathVariable Long id) {
        commentsService.changeCommentEnabledStatus(id, false);
    }

    @PutMapping("/activate/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void activateComment(@PathVariable Long id) {
        commentsService.changeCommentEnabledStatus(id, true);
    }
}
