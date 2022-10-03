package com.spring.blackcat.likes;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    public Optional<Likes> findByPostIdAndUserId(Long post_id, String user_id);
}
