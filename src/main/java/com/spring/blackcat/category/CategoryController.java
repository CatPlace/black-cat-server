package com.spring.blackcat.category;

import com.spring.blackcat.category.dto.CategoryResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/categories")
    public List<CategoryResDto> findAll() {
        return categoryService.findAll();
    }
}
