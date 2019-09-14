package com.onyourmind.OnYourMind.controller;

import com.onyourmind.OnYourMind.dto.PostDTO;
import com.onyourmind.OnYourMind.service.impl.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/posts")
public class PostController {

    @Autowired
    private PostServiceImpl postService;

    @GetMapping("/public")
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        return new ResponseEntity<>(postService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/public/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
        PostDTO post = postService.findById(id);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<PostDTO> addPost(@RequestBody PostDTO post) {
        PostDTO newPost = postService.addPost(post);
        return new ResponseEntity<>(newPost, HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<PostDTO> editPost(@RequestBody PostDTO post) {
        // @TODO: Finish this
        return null;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<PostDTO> deletePost(@PathVariable Long id) {
        // @TODO: Finish this
        return null;
    }
}
