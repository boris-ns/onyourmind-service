package com.onyourmind.OnYourMind.service;

import com.onyourmind.OnYourMind.dto.PostCommentDTO;

import java.util.List;

public interface PostCommentService {

    PostCommentDTO findById(Long id);
    List<PostCommentDTO> findAll();
}
