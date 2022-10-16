package com.spring.blackcat.likes;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.blackcat.code.PostType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

import static com.spring.blackcat.likes.QLikes.likes;
import static com.spring.blackcat.post.QPost.post;

//@RequiredArgsConstructor
public class LikesRepositoryImpl implements LikesRepositoryCustom {

    private final EntityManager em;

    private final JPAQueryFactory query;

    public LikesRepositoryImpl(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public Page<Likes> findPostByUserIdAndFilter(Pageable pageable, String userId, PostType postType) {
        List<Likes> results = query
                .select(likes)
                .from(likes)
                .join(likes.post, post)
                .where(likes.user.id.eq(userId), postTypeEqual(postType))
                .limit(1000)
                .fetch();
        return new PageImpl<>(results, pageable, results.size());
    }

    private static BooleanExpression postTypeEqual(PostType postType) {
        if (postType == null) {
            return null;
        }
        return post.postType.eq(postType);
    }
}
