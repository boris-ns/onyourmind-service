package com.onyourmind.OnYourMind.service.impl;

import com.onyourmind.OnYourMind.common.TimeProvider;
import com.onyourmind.OnYourMind.common.UserHelper;
import com.onyourmind.OnYourMind.dto.PostCommentDTO;
import com.onyourmind.OnYourMind.exception.ApiRequestException;
import com.onyourmind.OnYourMind.exception.ResourceNotFoundException;
import com.onyourmind.OnYourMind.model.Post;
import com.onyourmind.OnYourMind.model.PostComment;
import com.onyourmind.OnYourMind.repository.PostCommentRepository;
import com.onyourmind.OnYourMind.service.PostCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class PostCommentServiceImpl implements PostCommentService {

    @Autowired
    private PostCommentRepository postCommentRepository;

    @Autowired
    private TimeProvider timeProvider;

    @Autowired
    private UserHelper userHelper;

    @Autowired
    private PostServiceImpl postService;


    @Override
    public PostCommentDTO findById(Long id) {
        PostComment comment = this.getCommentFromRepository(id);
        return new PostCommentDTO(comment);
    }

    @Override
    public List<PostCommentDTO> findAll() {
        return postCommentRepository.findAll().stream()
                .map(comment -> new PostCommentDTO(comment)).collect(Collectors.toList());
    }

    @Override
    public PostCommentDTO addComment(PostCommentDTO comment) throws ApiRequestException {
        if (comment.getPostId() == null)
            throw new ApiRequestException("Post ID must be available in request.");

        if (comment.getText().isEmpty())
            throw new ApiRequestException("Text can't be empty");

        PostComment newComment = new PostComment();
        newComment.setDateTime(timeProvider.now());
        newComment.setAuthor(userHelper.getCurrentUser());
        newComment.setText(comment.getText());

        Post post = postService.getPostFromRepository(comment.getPostId());
        newComment.setPost(post);

        postCommentRepository.save(newComment);

        return new PostCommentDTO(newComment);
    }

    @Override
    public void deleteComment(Long id) {
        // @TODO: Finish this
    }

    @Override
    public PostCommentDTO editComment(PostCommentDTO comment) {
        // @TODO: Finish this
        return null;
    }

    private PostComment getCommentFromRepository(Long id) throws ResourceNotFoundException {
        try {
            PostComment comment = postCommentRepository.findById(id).get();
            return comment;
        } catch (NoSuchElementException e) {
            throw new ResourceNotFoundException("Comment with ID " + id + " doesn't exist");
        }
    }
}
