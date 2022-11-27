package com.spring.blackcat.category;

import com.spring.blackcat.common.response.ResponseDto;
import com.spring.blackcat.common.response.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseDto findAll() {
        return ResponseUtil.SUCCESS("모든 카테고리 조회 성공", categoryService.findAll());
    }
}
