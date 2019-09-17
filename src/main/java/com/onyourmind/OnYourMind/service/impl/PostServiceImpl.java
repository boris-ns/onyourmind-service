package com.onyourmind.OnYourMind.service.impl;

import com.onyourmind.OnYourMind.common.TimeProvider;
import com.onyourmind.OnYourMind.common.UserHelper;
import com.onyourmind.OnYourMind.dto.PostDTO;
import com.onyourmind.OnYourMind.exception.ApiRequestException;
import com.onyourmind.OnYourMind.exception.ResourceNotFoundException;
import com.onyourmind.OnYourMind.model.Post;
import com.onyourmind.OnYourMind.model.User;
import com.onyourmind.OnYourMind.repository.PostRepository;
import com.onyourmind.OnYourMind.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private TimeProvider timeProvider;

    @Autowired
    private UserHelper userHelper;


    @Override
    public PostDTO findById(Long id) {
        Post post = this.getPostFromRepository(id);
        return new PostDTO(post);
    }

    @Override
    public List<PostDTO> findAll() {
        return postRepository.findAll().stream()
                .map(post -> new PostDTO(post)).collect(Collectors.toList());
    }

    @Override
    public PostDTO addPost(PostDTO post) {
        Post newPost = new Post();
        newPost.setText(post.getText());
        newPost.setDateTime(timeProvider.now());
        newPost.setLikes(0);
        newPost.setDislikes(0);
        newPost.setEnabled(true);

        User author = userHelper.getCurrentUser();
        newPost.setAuthor(author);

        postRepository.save(newPost);

        return new PostDTO(newPost);
    }

    @Override
    public void deletePost(Long id) throws ApiRequestException {
        Post post = this.getPostFromRepository(id);
        User currentUser = userHelper.getCurrentUser();

        if (!post.getAuthor().equals(currentUser))
            throw new ApiRequestException("This user can't delete post with ID " + id);

        postRepository.delete(post);
    }

    @Override
    public PostDTO editPost(PostDTO post) throws ApiRequestException {
        Post postToEdit = this.getPostFromRepository(post.getId());
        User currentUser = userHelper.getCurrentUser();

        if (!postToEdit.getAuthor().equals(currentUser))
            throw new ApiRequestException("This user can't edit post with ID " + post.getId());

        postToEdit.setText(post.getText());
        postRepository.save(postToEdit);

        return new PostDTO(postToEdit);
    }

    @Override
    public void changePostEnabledStatus(Long id, boolean status) {
        Post post = this.getPostFromRepository(id);
        post.setEnabled(status);
        postRepository.save(post);
    }

    @Override
    public List<PostDTO> findAllPostsFromUser(Long id) {
        User user = userService.getUserFromRepository(id);
        return user.getPosts().stream()
                .map(post -> new PostDTO(post)).collect(Collectors.toList());
    }

    public Post getPostFromRepository(Long id) throws ResourceNotFoundException {
        try {
            Post post = postRepository.findById(id).get();
            return post;
        } catch (NoSuchElementException e) {
            throw new ResourceNotFoundException("Post with ID " + id + " doesn't exist.");
        }
    }
}
