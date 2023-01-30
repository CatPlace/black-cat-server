package com.spring.blackcat.common;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.PathBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class Querydsl {

    public static OrderSpecifier getOrder(Sort.Order order, EntityPathBase<?> target) {
        PathBuilder pathBuilder = new PathBuilder<>(target.getType(), target.getMetadata());
        OrderSpecifier orderSpecifier = new OrderSpecifier(
                order.isAscending() ? Order.ASC : Order.DESC,
                pathBuilder.get(order.getProperty())
        );
        return orderSpecifier;
    }

    public static OrderSpecifier[] getOrders(Pageable pageable, EntityPathBase<?> target) {
        List<OrderSpecifier<?>> orders = new ArrayList<>();
        for (Sort.Order order : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder<>(target.getType(), target.getMetadata());
            orders.add(new OrderSpecifier(
                    order.isAscending() ? com.querydsl.core.types.Order.ASC : com.querydsl.core.types.Order.DESC,
                    pathBuilder.get(order.getProperty())
            ));
        }
        return orders.toArray(OrderSpecifier[]::new);
    }
}
