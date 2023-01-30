package com.spring.blackcat.likes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long>, LikesRepositoryCustom {

    Long countByPostId(Long postId);

    Optional<Likes> findByPostIdAndUserId(Long postId, Long userId);

    List<Likes> findByPostIdInAndUserId(List<Long> postIds, Long userId);

    @Query("select l.post.id from Likes l where l.user.id = :userId")
    List<Long> findPostIdsByUserId(@Param("userId") Long userId);
}
