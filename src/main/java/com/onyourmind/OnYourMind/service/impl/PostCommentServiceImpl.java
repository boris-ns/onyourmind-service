package com.onyourmind.OnYourMind.service.impl;

import com.onyourmind.OnYourMind.dto.PostCommentDTO;
import com.onyourmind.OnYourMind.model.PostComment;
import com.onyourmind.OnYourMind.repository.PostCommentRepository;
import com.onyourmind.OnYourMind.service.PostCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostCommentServiceImpl implements PostCommentService {

    @Autowired
    private PostCommentRepository postCommentRepository;


    @Override
    public PostCommentDTO findById(Long id) {
        PostComment postComment = postCommentRepository.findById(id).get();

        if (postComment == null)
            return null;

        return new PostCommentDTO(postComment);
    }

    @Override
    public List<PostCommentDTO> findAll() {
        return postCommentRepository.findAll().stream()
                .map(comment -> new PostCommentDTO(comment)).collect(Collectors.toList());
    }
}
