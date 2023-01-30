package com.spring.blackcat.tattoo;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.blackcat.common.code.PostType;
import com.spring.blackcat.common.code.TattooType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.spring.blackcat.common.Querydsl.getOrders;
import static com.spring.blackcat.likes.QLikes.likes;
import static com.spring.blackcat.tattoo.QTattoo.tattoo;

public class TattooRepositoryImpl implements TattooRepositoryCustom {

    private final JPAQueryFactory query;

    public TattooRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public Page<Tattoo> findTattoos(Pageable pageable, String tattooType, Long addressId) {
        List<Tuple> results = query
                .select(tattoo.id,
                        tattoo.title,
                        tattoo.tattooType,
                        tattoo.description,
                        tattoo.category,
                        tattoo.price,
                        tattoo.user,
                        likes.count().as("likes"))
                .from(tattoo)
                .leftJoin(tattoo.likes, likes).on(likes.postType.eq(PostType.TATTOO).and(tattoo.id.eq(likes.post.id)))
                .where(eqTattooType(tattooType),
                        eqAddressId(addressId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .groupBy(tattoo.id)
                .orderBy(getOrders(pageable, tattoo))
                .fetch();

        List<Tattoo> tattooResults = new ArrayList<>();
        results.forEach(tuple ->
                tattooResults.add(new Tattoo(tuple.get(tattoo.id), tuple.get(tattoo.title), tuple.get(tattoo.description),
                        tuple.get(tattoo.price), tuple.get(tattoo.category), tuple.get(tattoo.tattooType), tuple.get(tattoo.user)))
        );

        return new PageImpl<>(tattooResults, pageable, results.size());
    }

    @Override
    public Page<Tattoo> findTattoosByCategoryId(Pageable pageable, Long categoryId, String tattooType, Long addressId) {
        List<Tattoo> results = query
                .selectFrom(tattoo)
                .where(eqCategoryId(categoryId),
                        eqTattooType(tattooType),
                        eqAddressId(addressId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(getOrders(pageable, tattoo))
                .fetch();

        return new PageImpl<>(results, pageable, results.size());
    }

    private BooleanExpression eqCategoryId(Long categoryId) {
        if (Objects.isNull(categoryId)) {
            return null;
        }
        return tattoo.category.id.eq(categoryId);
    }

    private static BooleanExpression eqTattooType(String tattooType) {
        if (StringUtils.isEmpty(tattooType)) {
            return null;
        }
        return tattoo.tattooType.eq(TattooType.valueOf(tattooType));
    }

    private static BooleanExpression eqAddressId(Long addressId) {
        if (Objects.isNull(addressId)) {
            return null;
        }
        return tattoo.user.address.id.eq(addressId);
    }
}
