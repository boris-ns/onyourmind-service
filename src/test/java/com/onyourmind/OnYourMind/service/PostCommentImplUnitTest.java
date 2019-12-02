package com.onyourmind.OnYourMind.service;

import com.onyourmind.OnYourMind.common.UserHelper;
import com.onyourmind.OnYourMind.dto.PostCommentDTO;
import com.onyourmind.OnYourMind.exception.ApiRequestException;
import com.onyourmind.OnYourMind.exception.ResourceNotFoundException;
import com.onyourmind.OnYourMind.model.Post;
import com.onyourmind.OnYourMind.model.PostComment;
import com.onyourmind.OnYourMind.model.User;
import com.onyourmind.OnYourMind.repository.PostCommentRepository;
import com.onyourmind.OnYourMind.service.impl.PostCommentServiceImpl;
import com.onyourmind.OnYourMind.service.impl.PostServiceImpl;
import com.onyourmind.OnYourMind.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostCommentImplUnitTest {

    @Autowired
    private PostCommentServiceImpl postCommentService;

    @MockBean
    private PostCommentRepository postCommentRepository;

    @MockBean
    private UserHelper userHelper;

    @MockBean
    private PostServiceImpl postService;

    @MockBean
    private UserServiceImpl userService;

    @Test
    public void whenFindByIdReturnComment() {
        final Long commentId = 1L;
        PostComment comment = new PostComment();
        comment.setId(commentId);
        Mockito.when(postCommentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        PostComment returnedComment = postCommentService.findById(commentId);
        assertEquals(commentId, returnedComment.getId());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenFindByIdThrowException() {
        final Long commentId = 1L;
        PostComment comment = postCommentService.findById(commentId);
    }

    @Test
    public void whenFindAllReturnListOfComments() {
        PostComment comment1 = new PostComment(); comment1.setId(1L);
        PostComment comment2 = new PostComment(); comment2.setId(2L);
        List<PostComment> comments = new ArrayList<>(List.of(comment1, comment2));
        Mockito.when(postCommentRepository.findAll()).thenReturn(comments);

        List<PostComment> returnedComments = postCommentService.findAll();
        assertEquals(2, returnedComments.size());
        assertEquals(Optional.of(1L).get(), returnedComments.get(0).getId());
        assertEquals(Optional.of(2L).get(), returnedComments.get(1).getId());
    }

    @Test
    public void whenAddCommentReturnNewComment() {
        final Long postId = 1L;
        final String commentText = "This is comment text";
        PostCommentDTO postCommentDto = new PostCommentDTO();
        postCommentDto.setPostId(postId);
        postCommentDto.setText(commentText);

        final Long authorId = 1L;
        User author = new User();
        author.setId(authorId);
        Mockito.when(userHelper.getCurrentUser()).thenReturn(author);

        // Do I need to mock timeProvider ??

        Post post = new Post();
        post.setId(postId);
        Mockito.when(postService.findById(postId)).thenReturn(post);

        final Long commentId = 1L;
        PostComment comment = new PostComment();
        comment.setId(commentId);
        comment.setText(commentText);
        comment.setAuthor(author);
        comment.setPost(post);
        comment.setEnabled(true);

        Mockito.when(postCommentRepository.save(any(PostComment.class))).thenReturn(comment);

        PostComment newComment = postCommentService.addComment(postCommentDto);
        assertEquals(Optional.of(commentId).get(), newComment.getId());
        assertEquals(commentText, newComment.getText());
        assertEquals(Optional.of(postId).get(), newComment.getPost().getId());
        assertEquals(Optional.of(authorId).get(), newComment.getAuthor().getId());
        assertTrue(newComment.isEnabled());
        // Provera datuma ?
    }

    @Test(expected = ApiRequestException.class)
    public void whenAddCommentWithNoPostIdThrowException() {
        PostCommentDTO commentDTO = new PostCommentDTO();
        commentDTO.setText("text");
        PostComment comment = postCommentService.addComment(commentDTO);
    }

    @Test(expected = ApiRequestException.class)
    public void whenAddCommentWithNoTextThrowException() {
        PostCommentDTO commentDTO = new PostCommentDTO();
        commentDTO.setId(1L);
        commentDTO.setText(null);
        PostComment comment = postCommentService.addComment(commentDTO);
    }

    @Test(expected = ApiRequestException.class)
    public void whenAddCommentWithEmptyTextThrowException() {
        PostCommentDTO commentDTO = new PostCommentDTO();
        commentDTO.setId(1L);
        commentDTO.setText("");
        PostComment comment = postCommentService.addComment(commentDTO);
    }

}
