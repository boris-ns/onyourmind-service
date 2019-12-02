package com.onyourmind.OnYourMind.repository;

import com.onyourmind.OnYourMind.model.Post;
import com.onyourmind.OnYourMind.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PostRepositoryUnitTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PostRepository postRepository;

    @Test
    public void whenFindByEnabledReturnListOfPosts() {
        User user = new User();
        user.setId(1L);

        Post post1 = new Post();
        post1.setEnabled(true);
        post1.setDislikes(0);
        post1.setLikes(0);
        post1.setDateTime(new Date());
        post1.setText("Text");
        post1.setAuthor(user);

        Post post2 = new Post();
        post2.setEnabled(false);
        post2.setDislikes(0);
        post1.setLikes(0);
        post2.setDateTime(new Date());
        post2.setText("Text");
        post2.setAuthor(user);

        Post post3 = new Post();
        post3.setEnabled(true);
        post3.setDislikes(0);
        post1.setLikes(0);
        post3.setDateTime(new Date());
        post3.setText("Text");
        post3.setAuthor(user);

        entityManager.persist(post1);
        entityManager.persist(post2);
        entityManager.persist(post3);
        entityManager.flush();

        List<Post> enabledPosts = postRepository.findByEnabled(true);
        assertEquals(2, enabledPosts.size());
        assertEquals(1L, enabledPosts.get(0).getId().longValue());
        assertEquals(3L, enabledPosts.get(1).getId().longValue());

        List<Post> disabledPosts = postRepository.findByEnabled(false);
        assertEquals(1, disabledPosts.size());
        assertEquals(2L, disabledPosts.get(0).getId().longValue());
    }
}
