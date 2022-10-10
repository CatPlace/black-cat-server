package com.spring.blackcat.category;

import com.spring.blackcat.category.dto.CategoryResDto;
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
    void findAll() {
        // given
        Category category1 = new Category("레터링", "Admin", "Admin");
        Category category2 = new Category("이레즈미", "Admin", "Admin");
        Category category3 = new Category("올드스쿨", "Admin", "Admin");

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