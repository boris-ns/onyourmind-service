package com.onyourmind.OnYourMind.repository;

import com.onyourmind.OnYourMind.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByEnabled(boolean enabled);
}
