package com.onyourmind.OnYourMind.controller;

import com.onyourmind.OnYourMind.dto.PostDTO;
import com.onyourmind.OnYourMind.exception.ResourceNotFoundException;
import com.onyourmind.OnYourMind.model.Post;
import com.onyourmind.OnYourMind.model.User;
import com.onyourmind.OnYourMind.service.impl.PostServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostControllerUnitTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private PostServiceImpl postService;

    @Test
    public void whenGetAllPostsReturnListOfPosts() {
        Post post1 = new Post(); post1.setId(1L);
        Post post2 = new Post(); post2.setId(2L);
        List<Post> posts = new ArrayList<>(List.of(post1, post2));
        Mockito.when(postService.findAll()).thenReturn(posts);

        // TODO: FIX this, why dosn't it work ???
        ResponseEntity<PostDTO[]> response = restTemplate.getForEntity("/api/posts/public", PostDTO[].class);
        PostDTO[] responseBody = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(posts.size(), responseBody.length);
        assertEquals(Optional.of(1L).get(), responseBody[0].getId());
        assertEquals(Optional.of(2L).get(), responseBody[1].getId());
    }

    @Test
    public void whenGetPostByIdReturnPost() {
        final Long postId = 1L;
        final String postText = "This is post text";
        final Long userId = 1L;
        Post post = new Post();
        post.setId(postId);
        post.setText(postText);

        User user = new User();
        user.setId(userId);
        user.setFirstName("First");
        user.setLastName("Name");

        post.setAuthor(user);
        post.setEnabled(true);
        post.setDislikes(0);
        post.setLikes(0);

        Date date = new Date();
        post.setDateTime(date);
        post.setComments(new HashSet<>());

        Mockito.when(postService.findById(postId)).thenReturn(post);

        ResponseEntity<PostDTO> response = restTemplate.getForEntity("/api/posts/public/" + postId, PostDTO.class);
        PostDTO responseBody = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(responseBody);
        assertEquals(postId, responseBody.getId());
        assertEquals(postText, responseBody.getText());
        assertEquals(userId, responseBody.getAuthorId());
        assertTrue(responseBody.isEnabled());
        assertEquals("First Name", responseBody.getAuthorName());
        assertTrue(responseBody.getComments().isEmpty());
        assertEquals(0, responseBody.getDislikes());
        assertEquals(0, responseBody.getLikes());
        assertEquals(date, responseBody.getDateTime());
    }

    @Test
    public void whenGetPostByIdThrowNotFound() {
        final Long postId = 222L;
        Mockito.when(postService.findById(postId)).thenThrow(new ResourceNotFoundException("Post does not exist"));

        ResponseEntity<PostDTO> response = restTemplate.getForEntity("/api/posts/public/" + postId, PostDTO.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void whenGetPostsFromUserReturnListOfPosts() {
        final Long userId = 1L;
        Post post1 = new Post(); post1.setId(1L);
        Post post2 = new Post(); post2.setId(2L);
        List<Post> posts = new ArrayList<>(List.of(post1, post2));

        Mockito.when(postService.findAllPostsFromUser(userId)).thenReturn(posts);

        // TODO: FIX
        // TODO: Again same error as above -> RestClientException: Error while extracting response for type
        ResponseEntity<PostDTO[]> response = restTemplate
                .getForEntity("/api/posts/public/user/" + userId, PostDTO[].class);
        PostDTO[] responseBody = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(responseBody);
        assertEquals(Optional.of(1L).get(), responseBody[0].getId());
        assertEquals(Optional.of(2L).get(), responseBody[1].getId());
    }
}
