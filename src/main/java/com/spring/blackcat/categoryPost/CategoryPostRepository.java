package com.spring.blackcat.categoryPost;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryPostRepository extends JpaRepository<CategoryPost, Long> {
    List<CategoryPost> findByPostId(Long postId);

    void deleteByPostId(Long postId);
}
