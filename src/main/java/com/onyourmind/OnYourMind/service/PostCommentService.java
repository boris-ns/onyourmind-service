package com.onyourmind.OnYourMind.service;

import com.onyourmind.OnYourMind.dto.PostCommentDTO;

import java.util.List;

public interface PostCommentService {

    PostCommentDTO findById(Long id);
    List<PostCommentDTO> findAll();
    PostCommentDTO addComment(PostCommentDTO comment);
    void deleteComment(Long id);
    PostCommentDTO editComment(PostCommentDTO comment);
    void changeCommentEnabledStatus(Long id, boolean status);
    List<PostCommentDTO> findAllCommentsFromUser(Long id);
}
