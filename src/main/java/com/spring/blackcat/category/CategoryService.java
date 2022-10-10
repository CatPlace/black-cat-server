package com.spring.blackcat.category;

import com.spring.blackcat.category.dto.CategoryResDto;

import java.util.List;

public interface CategoryService {

    List<CategoryResDto> findAll();
}
