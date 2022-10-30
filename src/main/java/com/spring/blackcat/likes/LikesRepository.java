package com.spring.blackcat.likes;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long>, LikesRepositoryCustom {

    Optional<Likes> findByPostIdAndUserId(Long postId, Long userId);
}
