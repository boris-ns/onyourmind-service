package com.onyourmind.OnYourMind.controller;

import com.onyourmind.OnYourMind.dto.PostDTO;
import com.onyourmind.OnYourMind.mappers.PostMapper;
import com.onyourmind.OnYourMind.model.Post;
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
        List<Post> posts = postService.findAll();
        return new ResponseEntity<>(PostMapper.toListDto(posts), HttpStatus.OK);
    }

    @GetMapping("/public/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
        Post post = postService.findById(id);
        return new ResponseEntity<>(PostMapper.toDto(post), HttpStatus.OK);
    }

    @GetMapping("/public/user/{id}")
    public ResponseEntity<List<PostDTO>> getPostsFromUser(@PathVariable Long id) {
        List<Post> posts = postService.findAllPostsFromUser(id);
        return new ResponseEntity<>(PostMapper.toListDto(posts), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PostDTO> addPost(@RequestBody PostDTO post) {
        Post newPost = postService.addPost(post);
        return new ResponseEntity<>(PostMapper.toDto(newPost), HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<PostDTO> editPost(@RequestBody PostDTO post) {
        Post editedPost = postService.editPost(post);
        return new ResponseEntity<>(PostMapper.toDto(editedPost), HttpStatus.OK);
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
