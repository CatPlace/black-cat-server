package com.spring.blackcat.likes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    Optional<Likes> findByPostIdAndUserId(Long postId, String userId);

    Page<Likes> findByPostId(Pageable pageable, Long postId);

    Page<Likes> findByUserId(Pageable pageable, String userId);
}
