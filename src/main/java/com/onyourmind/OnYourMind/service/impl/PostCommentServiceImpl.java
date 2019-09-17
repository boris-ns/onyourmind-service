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

        if (comment.getText() == null || comment.getText().isEmpty())
            throw new ApiRequestException("Text can't be empty");

        PostComment newComment = new PostComment();
        newComment.setDateTime(timeProvider.now());
        newComment.setAuthor(userHelper.getCurrentUser());
        newComment.setText(comment.getText());
        newComment.setEnabled(true);

        Post post = postService.getPostFromRepository(comment.getPostId());
        newComment.setPost(post);

        postCommentRepository.save(newComment);

        return new PostCommentDTO(newComment);
    }

    @Override
    public void deleteComment(Long id) throws ApiRequestException {
        PostComment comment = getCommentFromRepository(id);
        User currentUser = userHelper.getCurrentUser();

        if (!comment.getAuthor().equals(currentUser))
            throw new ApiRequestException("This user can't delete comment with ID " + id);

        postCommentRepository.delete(comment);
    }

    @Override
    public PostCommentDTO editComment(PostCommentDTO comment) throws ApiRequestException {
        if (comment.getId() == null)
            throw new ApiRequestException("You must send Comment ID");

        if (comment.getText() == null || comment.getText().isEmpty())
            throw new ApiRequestException("Text can't be empty");

        PostComment commentToEdit = this.getCommentFromRepository(comment.getId());
        User currentUser = userHelper.getCurrentUser();

        if (!commentToEdit.getAuthor().equals(currentUser))
            throw new ApiRequestException("This user can't edit comment with ID " + comment.getId());

        commentToEdit.setText(comment.getText());
        postCommentRepository.save(commentToEdit);

        return new PostCommentDTO(commentToEdit);
    }

    @Override
    public void changeCommentEnabledStatus(Long id, boolean status) {
        PostComment comment = this.getCommentFromRepository(id);
        comment.setEnabled(status);
        postCommentRepository.save(comment);
    }

    @Override
    public List<PostCommentDTO> findAllCommentsFromUser(Long id) {
        User user = userService.getUserFromRepository(id);
        return user.getComments().stream()
                .map(comment -> new PostCommentDTO(comment)).collect(Collectors.toList());
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
