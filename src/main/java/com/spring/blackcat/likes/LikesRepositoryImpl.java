package com.spring.blackcat.likes;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.blackcat.common.code.PostType;
import com.spring.blackcat.likes.dto.LikesPostResDto;
import com.spring.blackcat.likes.dto.LikesUserResDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

import static com.spring.blackcat.common.Querydsl.getOrder;
import static com.spring.blackcat.image.QImage.image;
import static com.spring.blackcat.likes.QLikes.likes;
import static com.spring.blackcat.post.QPost.post;

public class LikesRepositoryImpl implements LikesRepositoryCustom {

    private final JPAQueryFactory query;

    public LikesRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public Page<LikesUserResDto> findLikesUsersByPostId(Pageable pageable, Long postId) {
        List<LikesUserResDto> results = query
                .select(Projections.constructor(LikesUserResDto.class,
                        likes.id,
                        likes.user.id,
                        likes.user.name,
                        likes.createdDate))
                .from(likes)
                .join(likes.post, post)
                .where(postEqual(postId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(getOrder(pageable, likes))
                .fetch();
        return new PageImpl<>(results, pageable, results.size());
    }

    @Override
    public Page<LikesPostResDto> findLikesPostsByUserIdAndPostType(Pageable pageable, String userId, PostType postType) {
        List<LikesPostResDto> results = query
                .select(Projections.constructor(LikesPostResDto.class,
                        likes.id,
                        post.id,
                        post.title,
                        image.imageUrl,
                        post.postType,
                        likes.createdDate))
                .from(likes)
                .join(likes.post, post)
                .leftJoin(post.images, image)
                .where(userIdEqual(userId), postTypeEqual(postType), mainImage())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(getOrder(pageable, likes))
                .fetch();
        return new PageImpl<>(results, pageable, results.size());
    }

    private static BooleanExpression postEqual(Long postId) {
        return likes.post.id.eq(postId);
    }

    private static BooleanExpression userIdEqual(String userId) {
        return likes.user.id.eq(userId);
    }

    private static BooleanExpression postTypeEqual(PostType postType) {
        if (postType == null) {
            return null;
        }
        return post.postType.eq(postType);
    }

    private static BooleanExpression mainImage() {
        return image.isMain.isTrue();
    }
}
