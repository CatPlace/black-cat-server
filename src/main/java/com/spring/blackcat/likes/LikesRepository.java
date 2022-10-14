package com.spring.blackcat.likes;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    Optional<Likes> findByPostIdAndUserId(Long postId, String userId);

    List<Likes> findByPostId(Long postId);

    List<Likes> findByUserId(String userId);
}
