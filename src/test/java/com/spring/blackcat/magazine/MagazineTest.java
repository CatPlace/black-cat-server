package com.spring.blackcat.magazine;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MagazineTest {
    @Autowired
    MagazineRepository magazineRepository;

    @DisplayName("매거진 등록 시 메인 여부가 기본값으로 false로 저장되는 지 확인")
    @Test
    void getDefaultBooleanValueForIsMain() {
        //given
        List<Magazine> newMagazines = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            Magazine mm = createMagazine("test" + i);
            newMagazines.add(mm);
        }
        magazineRepository.saveAll(newMagazines);

        //when
        List<Magazine> magazineList = magazineRepository.findAll();

        //then
        for (Magazine m : magazineList) {
            assertThat(m.getIsMain()).isFalse();
        }

    }

    private static Magazine createMagazine(String title) {
        return new Magazine(title, 1L, 1L);
    }
}