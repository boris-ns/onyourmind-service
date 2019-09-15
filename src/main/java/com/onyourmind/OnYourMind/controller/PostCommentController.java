package com.onyourmind.OnYourMind.controller;

import com.onyourmind.OnYourMind.dto.PostCommentDTO;
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
        PostCommentDTO comment = commentsService.findById(id);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @GetMapping("/public")
    public ResponseEntity<List<PostCommentDTO>> getAllComments() {
        return new ResponseEntity<>(commentsService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<PostCommentDTO> addComment(@RequestBody PostCommentDTO comment) {
        PostCommentDTO newComment = commentsService.addComment(comment);
        return new ResponseEntity<>(newComment, HttpStatus.OK);
    }
}
