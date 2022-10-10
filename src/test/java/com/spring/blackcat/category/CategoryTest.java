package com.spring.blackcat.category;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CategoryTest {

    @Test
    void addChildCategory() {
        // given
        Category parentCategory = new Category("흑백", "Admin", "Admin");
        Category childCategory = new Category("레터링", "Admin", "Admin");

        // when
        parentCategory.addChildCategory(childCategory);
        boolean contains = parentCategory.getChildren().contains(childCategory);

        // then
        assertThat(contains).isTrue();
        assertThat(childCategory.getParent()).isEqualTo(parentCategory);

        System.out.println("parentCategory.getChildren().get(0) = " + parentCategory.getChildren().get(0).getName());
        System.out.println("childCategory.getParent() = " + childCategory.getParent().getName());
    }

    @Test
    void changeParentCategory() {
        // given
        Category firstParentCategory = new Category("흑백", "Admin", "Admin");
        Category secondParentCategory = new Category("미니", "Admin", "Admin");
        Category childCategory = new Category("레터링", "Admin", "Admin");

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

        System.out.println("firstParentCategory.getChildren().size() = " + firstParentCategory.getChildren().size());
        System.out.println("secondParentCategory.getChildren().get(0).getName() = " + secondParentCategory.getChildren().get(0).getName());
        System.out.println("childCategory.getParent() = " + childCategory.getParent().getName());
    }
}