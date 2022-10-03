package com.spring.blackcat.repository;

import com.spring.blackcat.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
