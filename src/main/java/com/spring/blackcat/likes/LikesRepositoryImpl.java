package com.spring.blackcat.likes;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.blackcat.common.code.ImageType;
import com.spring.blackcat.common.code.PostType;
import com.spring.blackcat.likes.dto.LikesPostResDto;
import com.spring.blackcat.likes.dto.LikesUserResDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

import static com.querydsl.core.types.Projections.constructor;
import static com.querydsl.sql.SQLExpressions.min;
import static com.querydsl.sql.SQLExpressions.select;
import static com.spring.blackcat.common.Querydsl.getOrder;
import static com.spring.blackcat.common.code.ImageType.POST;
import static com.spring.blackcat.common.code.ImageType.USER;
import static com.spring.blackcat.image.QImage.image;
import static com.spring.blackcat.likes.QLikes.likes;
import static com.spring.blackcat.post.QPost.post;
import static com.spring.blackcat.user.QUser.user;

public class LikesRepositoryImpl implements LikesRepositoryCustom {

    private final JPAQueryFactory query;

    public LikesRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public Page<LikesUserResDto> findLikesUsersByPostId(Pageable pageable, Long postId) {
        List<LikesUserResDto> results = query
                .select(constructor(LikesUserResDto.class,
                        likes.id,
                        user.id,
                        user.nickname,
                        image.imageUrl,
                        likes.createdDate))
                .from(likes)
                .join(likes.user, user)
                .leftJoin(image).on(image.imageType.eq(USER).and(user.id.eq(image.mappedId)))
                .where(postEqual(postId), isMainImage(USER, user.id))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(getOrder(pageable, likes))
                .fetch();
        return new PageImpl<>(results, pageable, results.size());
    }

    @Override
    public Page<LikesPostResDto> findLikesPostsByUserIdAndPostType(Pageable pageable, Long userId, PostType postType) {
        List<LikesPostResDto> results = query
                .select(constructor(LikesPostResDto.class,
                        likes.id,
                        post.id,
                        post.postType,
                        post.title,
                        image.imageUrl,
                        likes.createdDate))
                .from(likes)
                .join(likes.post, post)
                .leftJoin(image).on(image.imageType.eq(POST).and(post.id.eq(image.mappedId)))
                .where(userIdEqual(userId), postTypeEqual(postType), isMainImage(POST, post.id))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(getOrder(pageable, likes))
                .fetch();
        return new PageImpl<>(results, pageable, results.size());
    }

    private static BooleanExpression postEqual(Long postId) {
        return likes.post.id.eq(postId);
    }

    private static BooleanExpression userIdEqual(Long userId) {
        return likes.user.id.eq(userId);
    }

    private static BooleanExpression postTypeEqual(PostType postType) {
        return postType != null ? likes.post.postType.eq(postType) : null;
    }

    private static BooleanExpression isMainImage(ImageType imageType, NumberPath<Long> mappedId) {
        return image.id.isNull().or(image.createdDate.eq(
                select(min(image.createdDate))
                        .from(image)
                        .where(image.imageType.eq(imageType), image.mappedId.eq(mappedId))));
    }
}
