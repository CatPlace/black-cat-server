package com.spring.blackcat.category;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CategoryTest {

    @Test
    @DisplayName("하위 카테고리 등록")
    void addChildCategory() {
        // given
        Category parentCategory = new Category("흑백", 1L, 1L);
        Category childCategory = new Category("레터링", 1L, 1L);

        // when
        parentCategory.addChildCategory(childCategory);
        boolean contains = parentCategory.getChildren().contains(childCategory);

        // then
        assertThat(contains).isTrue();
        assertThat(childCategory.getParent()).isEqualTo(parentCategory);
    }

    @Test
    @DisplayName("상위 카테고리 등록")
    void changeParentCategory() {
        // given
        Category firstParentCategory = new Category("흑백", 1L, 1L);
        Category secondParentCategory = new Category("미니", 1L, 1L);
        Category childCategory = new Category("레터링", 1L, 1L);

        // when
        childCategory.changeParent(firstParentCategory);
        Category firstParent = childCategory.getParent();
        boolean containsFirst = firstParentCategory.getChildren().contains(childCategory);

        childCategory.changeParent(secondParentCategory);
        Category secondParent = childCategory.getParent();
        boolean containsSecond = secondParentCategory.getChildren().contains(childCategory);

        boolean containsFirstAfterChange = firstParentCategory.getChildren().contains(childCategory);

        // then
        assertThat(containsFirst).isTrue();
        assertThat(containsSecond).isTrue();
        assertThat(containsFirstAfterChange).isFalse();
        assertThat(firstParent).isEqualTo(firstParentCategory);
        assertThat(secondParent).isEqualTo(secondParentCategory);
    }
}