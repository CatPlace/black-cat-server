package com.spring.blackcat.post;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByIdIn(List<Long> postIds);

    Optional<Post> findById(Long id);
}
