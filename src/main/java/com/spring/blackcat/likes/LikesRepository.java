package com.spring.blackcat.likes;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long>, LikesRepositoryCustom {

    Long countByPostId(Long postId);

    Optional<Likes> findByPostIdAndUserId(Long postId, Long userId);

    List<Likes> findByPostIdInAndUserId(List<Long> postIds, Long userId);
}
