package com.spring.blackcat.category;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CategoryRepositoryTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    @DisplayName("ID로 카테고리 조회")
    void findById() {
        // given
        Category category = new Category("레터링", 1L, 1L);
        categoryRepository.save(category);

        // when
        Category findCategory = categoryRepository.findById(category.getId()).orElseGet(Category::new);

        // then
        assertThat(findCategory).isEqualTo(category);
    }

    @Test
    @DisplayName("이름으로 카테고리 조회")
    void findByName() {
        // given
        Category category = new Category("레터링", 1L, 1L);
        categoryRepository.save(category);

        // when
        Category findCategory = categoryRepository.findByName(category.getName()).orElseGet(Category::new);

        // then
        assertThat(findCategory).isEqualTo(category);
    }

    @Test
    @DisplayName("전체 카테고리 조회")
    void findAll() {
        // given
        Category category1 = new Category("레터링", 1L, 1L);
        Category category2 = new Category("이레즈미", 1L, 1L);
        Category category3 = new Category("올드스쿨", 1L, 1L);

        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);

        long totalCount = categoryRepository.count();

        // when
        List<Category> allCategories = categoryRepository.findAll();

        // then
        assertThat(allCategories.size()).isEqualTo(totalCount);
    }

    @Test
    @DisplayName("ID로 카테고리 삭제")
    void deleteByID() {
        // given
        Category category = new Category("레터링", 1L, 1L);
        categoryRepository.save(category);

        // when
        categoryRepository.deleteById(category.getId());
        boolean present = categoryRepository.findById(category.getId()).isPresent();

        // then
        assertThat(present).isFalse();
    }

    @Test
    @DisplayName("전체 카테고리 삭제")
    void deleteAll() {
        // given
        Category category1 = new Category("레터링", 1L, 1L);
        Category category2 = new Category("이레즈미", 1L, 1L);
        Category category3 = new Category("올드스쿨", 1L, 1L);

        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);

        long totalCount = categoryRepository.count();

        // when
        categoryRepository.deleteAll();

        long deletedCount = categoryRepository.count();

        // then
        assertThat(totalCount).isGreaterThan(deletedCount);
        assertThat(deletedCount).isEqualTo(0);
    }
}