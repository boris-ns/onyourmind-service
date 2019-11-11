package com.onyourmind.OnYourMind.service;

import com.onyourmind.OnYourMind.dto.PostCommentDTO;
import com.onyourmind.OnYourMind.model.PostComment;

import java.util.List;

public interface PostCommentService {

    PostComment findById(Long id);
    List<PostComment> findAll();
    PostComment addComment(PostCommentDTO comment);
    void deleteComment(Long id);
    PostComment editComment(PostCommentDTO comment);
    void changeCommentEnabledStatus(Long id, boolean status);
    List<PostComment> findAllCommentsFromUser(Long id);
}
