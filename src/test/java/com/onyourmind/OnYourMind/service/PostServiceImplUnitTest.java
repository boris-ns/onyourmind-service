package com.onyourmind.OnYourMind.service;

import com.onyourmind.OnYourMind.common.UserHelper;
import com.onyourmind.OnYourMind.dto.PostDTO;
import com.onyourmind.OnYourMind.exception.ApiRequestException;
import com.onyourmind.OnYourMind.exception.ResourceNotFoundException;
import com.onyourmind.OnYourMind.model.Post;
import com.onyourmind.OnYourMind.model.User;
import com.onyourmind.OnYourMind.repository.PostRepository;
import com.onyourmind.OnYourMind.service.impl.PostServiceImpl;
import com.onyourmind.OnYourMind.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostServiceImplUnitTest {

    @Autowired
    private PostServiceImpl postService;

    @MockBean
    private PostRepository postRepository;

    @MockBean
    private UserHelper userHelper;

    @MockBean
    private UserServiceImpl userService;

    @Test
    public void whenFindByIdReturnPost() {
        final Long id = 1L;
        Post post = new Post();
        post.setId(id);

        Mockito.when(postRepository.findById(id)).thenReturn(Optional.of(post));
        Post wantedPost = postService.findById(id);
        assertEquals(id, wantedPost.getId());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenFindByIdThrowException() {
        // no need to mock because the postRepository.findById will return null, and that's what we want
        Post post = postService.findById(123L);
    }

    @Test
    public void whenFindAllReturnListOfPosts() {
        List<Post> posts = new ArrayList<>();
        Post post1 = new Post();
        post1.setId(1L);
        Post post2 = new Post();
        post2.setId(2L);
        posts.add(post1);
        posts.add(post2);

        Mockito.when(postRepository.findAll()).thenReturn(posts);
        List<Post> returnedPosts = postService.findAll();
        assertEquals(2, returnedPosts.size());
        assertEquals(Optional.of(1L).get(), returnedPosts.get(0).getId());
        assertEquals(Optional.of(2L).get(), returnedPosts.get(1).getId());
    }

    @Test
    public void whenFindAllReturnEmptyList() {
        Mockito.when(postRepository.findAll()).thenReturn(new ArrayList<Post>());
        List<Post> posts = postService.findAll();
        assertEquals(0, posts.size());
    }

    @Test
    public void whenAddPostReturnNewPost() {
        // Does this make any sense ? I am making new mock-object and function that i'm testing is
        // going to just return it.
        User user = new User();
        user.setId(1L);
        Mockito.when(userHelper.getCurrentUser()).thenReturn(user);

        PostDTO post = new PostDTO();
        post.setText("This is text");

        Post expectedPost = new Post();
        expectedPost.setId(1L);
        expectedPost.setEnabled(true);
        expectedPost.setText("New post");
        expectedPost.setAuthor(user);
        expectedPost.setLikes(0);
        expectedPost.setDislikes(0);

        Mockito.when(postRepository.save(any(Post.class))).thenReturn(expectedPost);
        Post addedPost = postService.addPost(post);

        assertEquals(expectedPost.getId(), addedPost.getId());
        assertEquals(expectedPost.getText(), addedPost.getText());
        assertEquals(expectedPost.getAuthor().getId(), addedPost.getAuthor().getId());
        assertEquals(expectedPost.getLikes(), addedPost.getLikes());
        assertEquals(expectedPost.getDislikes(), addedPost.getDislikes());
        assertTrue(addedPost.isEnabled());
        assertEquals(0, addedPost.getComments().size());
        // test new Date() // now ????
    }

    @Test
    public void deletePostSuccessfull() {
        User user = new User();
        user.setId(1L);
        Mockito.when(userHelper.getCurrentUser()).thenReturn(user);

        Long postId = 1L;
        Post postToDelete = new Post();
        postToDelete.setId(postId);
        postToDelete.setAuthor(user);

        Mockito.when(postRepository.findById(postId)).thenReturn(Optional.of(postToDelete));
        postService.deletePost(postId);
    }

    @Test(expected = ApiRequestException.class)
    public void deletePostException() {
        User loggedInUser = new User();
        loggedInUser.setId(123L);
        Mockito.when(userHelper.getCurrentUser()).thenReturn(loggedInUser);

        User author = new User();
        author.setId(1L);

        Long postId = 1L;
        Post postToDelete = new Post();
        postToDelete.setId(postId);
        postToDelete.setAuthor(author);

        Mockito.when(postRepository.findById(postId)).thenReturn(Optional.of(postToDelete));
        postService.deletePost(postId);
    }

    @Test
    public void whenFindAllPostsFromUser() {
        final Long userId = 1L;
        User user = new User();
        user.setId(userId);

        Post post1 = new Post(); post1.setId(1L);
        Post post2 = new Post(); post2.setId(2L);
        Set<Post> posts = new HashSet<>();
        posts.add(post1);
        posts.add(post2);

        user.setPosts(posts);

        Mockito.when(userService.findById(userId)).thenReturn(user);
        List<Post> usersPosts = postService.findAllPostsFromUser(userId);

        assertEquals(2, usersPosts.size());
        assertEquals(Optional.of(1L).get(), usersPosts.get(0).getId());
        assertEquals(Optional.of(2L).get(), usersPosts.get(1).getId());
    }

    @Test
    public void whenChangePostEnabledStatus() {
        final Long postId = 1L;
        Post post = new Post();
        post.setId(postId);
        post.setEnabled(true);

        Mockito.when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        Mockito.when(postRepository.save(any(Post.class))).thenReturn(post);

        postService.changePostEnabledStatus(postId, false);
        assertFalse(post.isEnabled());

        postService.changePostEnabledStatus(postId, true);
        assertTrue(post.isEnabled());
    }

    @Test
    public void whenEditPostReturnEditedPost() {
        final Long postId = 1L;
        final String newText = "This is new text";
        PostDTO postEditInfo = new PostDTO();
        postEditInfo.setId(postId);
        postEditInfo.setText(newText);

        User author = new User();
        author.setId(1L);
        Mockito.when(userHelper.getCurrentUser()).thenReturn(author);

        Post postToEdit = new Post();
        postToEdit.setId(postId);
        postToEdit.setText("This is old text");
        postToEdit.setAuthor(author);

        Mockito.when(postRepository.findById(postId)).thenReturn(Optional.of(postToEdit));
        Mockito.when(postRepository.save(any(Post.class))).thenReturn(postToEdit);

        Post returnedPost = postService.editPost(postEditInfo);
        assertEquals(postId, returnedPost.getId());
        assertEquals(newText, returnedPost.getText());
    }

    @Test(expected = ApiRequestException.class)
    public void whenEditPostThrowException() {
        final Long postId = 1L;
        final String newText = "This is new text";
        PostDTO postEditInfo = new PostDTO();
        postEditInfo.setId(postId);
        postEditInfo.setText(newText);

        User author = new User();
        author.setId(1L);

        Post postToEdit = new Post();
        postToEdit.setId(postId);
        postToEdit.setText("This is old text");
        postToEdit.setAuthor(author);

        User loggedInUser = new User();
        loggedInUser.setId(123L);
        Mockito.when(userHelper.getCurrentUser()).thenReturn(loggedInUser);
        Mockito.when(postRepository.findById(postId)).thenReturn(Optional.of(postToEdit));

        Post returnedPost = postService.editPost(postEditInfo);
    }
}
