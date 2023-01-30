package com.spring.blackcat.common;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.PathBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static com.querydsl.core.types.Order.ASC;
import static com.querydsl.core.types.Order.DESC;
import static com.spring.blackcat.likes.QLikes.likes;

public class Querydsl {

    public static OrderSpecifier<?>[] getOrders(Pageable pageable, EntityPathBase<?> target) {
        List<OrderSpecifier<?>> orders = new ArrayList<>();
        for (Sort.Order order : pageable.getSort()) {
            orders.add(getOrder(order, target));
        }
        return orders.toArray(OrderSpecifier<?>[]::new);
    }

    public static OrderSpecifier<?> getOrder(Sort.Order order, EntityPathBase<?> target) {
        Order sortBy = order.isAscending() ? ASC : DESC;
        PathBuilder<?> pathBuilder = new PathBuilder<>(target.getType(), target.getMetadata());
        switch (order.getProperty()) {
            case "likesCount":
                return new OrderSpecifier<>(sortBy, likes.count());
            default:
                return new OrderSpecifier(sortBy, pathBuilder.get(order.getProperty()));
        }
    }
}
