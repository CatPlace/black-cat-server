package com.spring.blackcat.likes;

import com.spring.blackcat.code.PostType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LikesRepositoryCustom {
    Page<Likes> findPostByUserIdAndFilter(Pageable pageable, String Id, PostType postType);
}
