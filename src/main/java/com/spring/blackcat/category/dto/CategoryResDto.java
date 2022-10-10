package com.spring.blackcat.category.dto;

import com.spring.blackcat.category.Category;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryResDto {
    private Long id;
    private String name;
    private long count;

    public CategoryResDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.count = category.getTattoos().size();
    }
}