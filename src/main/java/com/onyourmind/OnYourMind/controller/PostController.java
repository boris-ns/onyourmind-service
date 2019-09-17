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

    @GetMapping("/public/user/{id}")
    public ResponseEntity<List<PostDTO>> getPostsFromUser(@PathVariable Long id) {
        return new ResponseEntity<>(postService.findAllPostsFromUser(id), HttpStatus.OK);
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
        PostDTO editedPost = postService.editPost(post);
        return new ResponseEntity<>(editedPost, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }

    @PutMapping("/deactivate/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deactivatePost(@PathVariable Long id) {
        postService.changePostEnabledStatus(id, false);
    }

    @PutMapping("/activate/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void activatePost(@PathVariable Long id) {
        postService.changePostEnabledStatus(id, true);
    }
}
