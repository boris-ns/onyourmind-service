package com.onyourmind.OnYourMind.service.impl;

import com.onyourmind.OnYourMind.common.TimeProvider;
import com.onyourmind.OnYourMind.common.UserHelper;
import com.onyourmind.OnYourMind.dto.PostCommentDTO;
import com.onyourmind.OnYourMind.exception.ApiRequestException;
import com.onyourmind.OnYourMind.exception.ResourceNotFoundException;
import com.onyourmind.OnYourMind.model.Post;
import com.onyourmind.OnYourMind.model.PostComment;
import com.onyourmind.OnYourMind.model.User;
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

    @Autowired
    private UserServiceImpl userService;


    @Override
    public PostComment findById(Long id) {
        try {
            PostComment comment = postCommentRepository.findById(id).get();
            return comment;
        } catch (NoSuchElementException e) {
            throw new ResourceNotFoundException("Comment with ID " + id + " doesn't exist");
        }
    }

    @Override
    public List<PostComment> findAll() {
        return postCommentRepository.findAll();
    }

    @Override
    public PostComment addComment(PostCommentDTO comment) throws ApiRequestException {
        if (comment.getPostId() == null)
            throw new ApiRequestException("Post ID must be available in request.");

        if (comment.getText() == null || comment.getText().isEmpty())
            throw new ApiRequestException("Text can't be empty");

        PostComment newComment = new PostComment();
        newComment.setDateTime(timeProvider.now());
        newComment.setAuthor(userHelper.getCurrentUser());
        newComment.setText(comment.getText());
        newComment.setEnabled(true);

        Post post = postService.findById(comment.getPostId());
        newComment.setPost(post);

        postCommentRepository.save(newComment);

        return newComment;
    }

    @Override
    public void deleteComment(Long id) throws ApiRequestException {
        PostComment comment = findById(id);
        User currentUser = userHelper.getCurrentUser();

        if (!comment.getAuthor().equals(currentUser))
            throw new ApiRequestException("This user can't delete comment with ID " + id);

        postCommentRepository.delete(comment);
    }

    @Override
    public PostComment editComment(PostCommentDTO comment) throws ApiRequestException {
        if (comment.getId() == null)
            throw new ApiRequestException("You must send Comment ID");

        if (comment.getText() == null || comment.getText().isEmpty())
            throw new ApiRequestException("Text can't be empty");

        PostComment commentToEdit = this.findById(comment.getId());
        User currentUser = userHelper.getCurrentUser();

        if (!commentToEdit.getAuthor().equals(currentUser))
            throw new ApiRequestException("This user can't edit comment with ID " + comment.getId());

        commentToEdit.setText(comment.getText());
        postCommentRepository.save(commentToEdit);

        return commentToEdit;
    }

    @Override
    public void changeCommentEnabledStatus(Long id, boolean status) {
        PostComment comment = this.findById(id);
        comment.setEnabled(status);
        postCommentRepository.save(comment);
    }

    @Override
    public List<PostComment> findAllCommentsFromUser(Long id) {
        User user = userService.findById(id);
        return user.getComments().stream().collect(Collectors.toList());
    }
}
