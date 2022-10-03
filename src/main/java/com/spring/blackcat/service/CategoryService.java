package com.spring.blackcat.service;

import com.spring.blackcat.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    public List<CategoryDto> findAll();
}
