package com.spring.blackcat.category;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class CategoryControllerTest {

    @Autowired
    CategoryController categoryController;

    @Autowired
    private MockMvc mock;

    @Test
    @DisplayName("전체 카테고리 조회")
    void getCategoriesTest() throws Exception {
        mock.perform(get("/api/v1/categories")).andExpect(status().isOk());
    }
}