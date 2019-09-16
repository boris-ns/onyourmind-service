package com.onyourmind.OnYourMind.repository;

import com.onyourmind.OnYourMind.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Authority findByName(String name);
}
