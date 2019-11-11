package com.onyourmind.OnYourMind.service;

import com.onyourmind.OnYourMind.dto.PostDTO;
import com.onyourmind.OnYourMind.model.Post;

import java.util.List;

public interface PostService {

    Post findById(Long id);
    List<Post> findAll();
    Post addPost(PostDTO post);
    void deletePost(Long id);
    Post editPost(PostDTO post);
    void changePostEnabledStatus(Long id, boolean status);
    List<Post> findAllPostsFromUser(Long id);
}
