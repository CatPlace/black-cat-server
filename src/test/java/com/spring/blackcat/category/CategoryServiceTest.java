package com.spring.blackcat.category;

import com.spring.blackcat.category.dto.CategoryResDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CategoryServiceTest {

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryRepository categoryRepository;

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
        List<CategoryResDto> allCategories = categoryService.findAll();

        // then
        assertThat(allCategories.size()).isEqualTo(totalCount);
        assertThat(allCategories.get(0)).isInstanceOf(CategoryResDto.class);
    }
}