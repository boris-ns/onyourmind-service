package com.onyourmind.OnYourMind.repository;

import com.onyourmind.OnYourMind.model.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {

}
