package com.onyourmind.OnYourMind.service.impl;

import com.onyourmind.OnYourMind.common.TimeProvider;
import com.onyourmind.OnYourMind.dto.PostDTO;
import com.onyourmind.OnYourMind.exception.ResourceNotFoundException;
import com.onyourmind.OnYourMind.model.Post;
import com.onyourmind.OnYourMind.model.User;
import com.onyourmind.OnYourMind.repository.PostRepository;
import com.onyourmind.OnYourMind.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TimeProvider timeProvider;


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

        User author = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        newPost.setAuthor(author);

        postRepository.save(newPost);

        return new PostDTO(newPost);
    }

    @Override
    public void deletePost(Long id) throws ResourceNotFoundException {
        Post post = this.getPostFromRepository(id);
        postRepository.delete(post);
    }

    @Override
    public PostDTO editPost(PostDTO post) throws ResourceNotFoundException {
        Post postToEdit = this.getPostFromRepository(post.getId());
        postToEdit.setText(post.getText());
        postRepository.save(postToEdit);

        return new PostDTO(postToEdit);
    }

    private Post getPostFromRepository(Long id) throws ResourceNotFoundException {
        try {
            Post post = postRepository.findById(id).get();
            return post;
        } catch (NoSuchElementException e) {
            throw new ResourceNotFoundException("Post with ID " + id + " doesn't exist.");
        }
    }
}
