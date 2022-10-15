package com.spring.blackcat.magazine;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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
        List<Magazine> magazines = new ArrayList<>();

        for (int i = 0; i < 234; i++) {
            Magazine m = createMagazine("test" + i);
            magazines.add(m);
        }
        magazineRepository.saveAll(magazines);

        //when
        List<Magazine> magazineId = new ArrayList<>(magazineRepository.findAll());

        //then
        mockMvc.perform(get("/magazines?page=0"))
                .andDo(print())
                .andExpect(jsonPath("status").exists())
                .andExpect(jsonPath("code").exists())
                .andExpect(jsonPath("$.data.totalElements").value(234))
                .andExpect(jsonPath("$.data.content").isArray());
    }

    @DisplayName("특정 매거진 조회")
    @Test
    void getSpecificMagazineTest() throws Exception {
        //given
        Long id = Long.valueOf(39L);
        //when

        //then
        mockMvc.perform(get("/magazines/" + String.valueOf(id)))
                .andDo(print())
                .andExpect(jsonPath("status").exists())
                .andExpect(jsonPath("code").exists())
                .andExpect(jsonPath("data").isArray());
    }

    @DisplayName("매거진 등록")
    @Test
    void registerMagazineTest() {
        //given

        //when

        //then
    }

    private static Magazine createMagazine(String title) {
        return new Magazine(title, "Admin", "Admin");
    }

}