package com.onyourmind.OnYourMind.service;

import com.onyourmind.OnYourMind.dto.PostDTO;

import java.util.List;

public interface PostService {

    PostDTO findById(Long id);
    List<PostDTO> findAll();
    PostDTO addPost(PostDTO post);
    void removePost(Long id);
    PostDTO editPost(PostDTO post);
}
