package com.spring.blackcat.tattoo;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.blackcat.common.code.TattooType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;

import static com.spring.blackcat.common.Querydsl.getOrder;
import static com.spring.blackcat.tattoo.QTattoo.tattoo;

public class TattooRepositoryImpl implements TattooRepositoryCustom {

    private final JPAQueryFactory query;

    public TattooRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public Page<Tattoo> findTattoos(Pageable pageable, String tattooType, Long addressId) {
        List<Tattoo> results = query
                .selectFrom(tattoo)
                .where(eqTattooType(tattooType),
                        eqAddressId(addressId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(getOrder(pageable, tattoo))
                .fetch();

        return new PageImpl<>(results, pageable, results.size());
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
                .orderBy(getOrder(pageable, tattoo))
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

        return tattoo.register.address.id.eq(addressId);
    }
}
