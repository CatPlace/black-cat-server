package com.spring.blackcat.magazine;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class MagazineControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MagazineRepository magazineRepository;
    @Test
    @DisplayName("전체 매거진 목록 조회")
    void getAllMagazineTest() throws Exception {
        //given
        //initDB에서 id 39L, 40L의 데이터가 입력된 상태
        //when
        List<Long> magazineId = magazineRepository.findAll().stream().map(m -> m.getId()).collect(Collectors.toList());
        //then
        mockMvc.perform(get("/magazines"))
                .andDo(print())
                .andExpect(jsonPath("status").exists())
                .andExpect(jsonPath("$.data[0].id").value(magazineId.get(0).intValue()))
                .andExpect(jsonPath("$.data[1].id").value(magazineId.get(1).intValue()))
                .andExpect(jsonPath("code").exists())
                .andExpect(jsonPath("data").isArray());
    }
    @DisplayName("특정 매거진 조회")
    @Test
    void getSpecificMagazineTest() throws Exception {
        //given
        Long id = Long.valueOf(39L);
        //when

        //then
        mockMvc.perform(get("/magazines/"+ String.valueOf(id)))
                .andDo(print())
                .andExpect(jsonPath("status").exists())
                .andExpect(jsonPath("code").exists())
                .andExpect(jsonPath("data").isArray());
    }

    @DisplayName("매거진 등록")
    @Test
    void registerMagazineTest(){
        //given

        //when

        //then
    }


}