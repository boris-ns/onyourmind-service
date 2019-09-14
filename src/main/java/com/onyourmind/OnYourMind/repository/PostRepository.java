package com.onyourmind.OnYourMind.repository;

import com.onyourmind.OnYourMind.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
