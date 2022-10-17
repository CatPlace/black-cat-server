package com.spring.blackcat.magazine;

import com.spring.blackcat.code.CellType;
import com.spring.blackcat.code.FontWeightType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class CellTest {

    @Autowired
    private CellRepository cellRepository;

    @DisplayName("Cell 추가 할때 null 값 전달하여 save시 기본값이 저장되는 지 확인")
    @Test
    void defaultColumnValueTest() {
        //given
        Cell cell = Cell.builder()
                .cellType(CellType.TEXTCELL)
                .text("wow")
                .build();

        //when
        cellRepository.save(cell);
        Cell res = cellRepository.findById(1L).get();

        //then
        assertThat(res.getFontWeight()).isEqualTo(FontWeightType.REGULAR);
        assertThat(res.getLayoutHeight()).isEqualTo(10L);
        
    }
}
