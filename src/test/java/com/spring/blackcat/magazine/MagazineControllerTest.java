package com.spring.blackcat.magazine;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

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
    @Test
    @DisplayName("전체 매거진 목록 조회")
    void getAllMagazineTest() throws Exception {
        //given

        //when

        //then
        mockMvc.perform(get("/magazines"))
                .andDo(print());
    }
    @DisplayName("특정 매거진 조회")
    @Test
    void getSpecificMagazineTest(){
        //given
        Long id = Long.valueOf(39L);

        //when

        //then

    }

    @DisplayName("매거진 등록")
    @Test
    void registerMagazineTest(){
        //given

        //when

        //then
    }


}