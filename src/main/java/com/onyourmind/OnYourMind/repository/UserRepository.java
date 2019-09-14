package com.onyourmind.OnYourMind.repository;

import com.onyourmind.OnYourMind.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
