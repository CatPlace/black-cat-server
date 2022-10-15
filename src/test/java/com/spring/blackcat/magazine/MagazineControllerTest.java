package com.spring.blackcat.magazine;

import com.spring.blackcat.code.CellType;
import com.spring.blackcat.code.FontWeightType;
import com.spring.blackcat.code.TextAlignmentType;
import com.spring.blackcat.code.TextColor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
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
        Magazine magazine = createMagazine("Test");

        List<Cell> cellList1 = createCells("타투 말은 많이 들어보고 주변에서도 요즘 많이 하던데 뭐가 뭔지 잘 모르겠어",
                "사람마다 모양, 색깔도 다르던데... 그리고 그거 아픈거 아냐?",
                "하는 당신을 위해 타투이스트 깜냥이가 모두 답변해드리겠습니닷!");

        List<Cell> cellList2 = createCells("2010년 연구에 따르면 18세에서 29세 사이의 사람들 중 무려 38%가 일생에 한 번 이상 타투를 해본 경험이 있다고 합니다.",
                "약 10년이 넘게 지난 지금 타투에 관한 인식이 이전보다 많이 좋아졌기 때문에 훨씬 더 많은 사람이 타투를 해본 경험이 있을 것입니다.",
                "하지만 처음 타투를 하기로 다짐했다면 '타투를 하면 아프나요?'가 자연스러운 질문일 것입니다.");

        cellList1.addAll(cellList2);

        cellList1.forEach(c -> c.changeMagazine(magazine));

        //when
        magazineRepository.save(magazine);
        Magazine newMagazine = magazineRepository.findById(1L).get();

        //then
        mockMvc.perform(get("/magazines/" + newMagazine.getId()))
                .andDo(print())
                .andExpect(jsonPath("status").exists())
                .andExpect(jsonPath("code").exists())
                .andExpect(jsonPath("data").isArray())
                .andExpect(jsonPath("data", hasSize(cellList1.size())));
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

    private static Cell createCell(
            CellType cellType, String text, Long fontSize,
            TextColor textColor, TextAlignmentType textAlignment, FontWeightType fontWeight,
            String imageUrlString, Long imageCornerRadius,
            Long layoutHeight, Long layoutWidth,
            Long layoutLeadingInset, Long layoutTrailingInset,
            Long layoutTopInset, Long layoutBottomInset) {
        return Cell.builder()
                .cellType(cellType)
                .text(text)
                .fontSize(fontSize)
                .textColor(textColor)
                .textAlignment(textAlignment)
                .fontWeight(fontWeight)
                .imageUrlString(imageUrlString)
                .imageCornerRadius(imageCornerRadius)
                .layoutHeight(layoutHeight)
                .layoutWidth(layoutWidth)
                .layoutLeadingInset(layoutLeadingInset)
                .layoutTrailingInset(layoutTrailingInset)
                .layoutTopInset(layoutTopInset)
                .layoutBottomInset(layoutBottomInset)
                .build();
    }

    private static List<Cell> createCells(String text1, String text2, String text3) {
        List<Cell> cellList = new ArrayList<>();
        cellList.add(createCell(CellType.EMPTYCELL, null, 12L,
                TextColor.BLACK, TextAlignmentType.LEFT, FontWeightType.REGULAR,
                null, 0L, 10L,
                10L, 20L, 20L,
                20L, 20L));
        cellList.add(createCell(CellType.IMAGECELL, null, 12L,
                TextColor.GRAY, TextAlignmentType.RIGHT, FontWeightType.MEDIUM,
                "https://이미지링크", 0L, 10L,
                10L, 20L, 20L,
                20L, 20L));
        cellList.add(createCell(CellType.TEXTCELL, text1, 10L,
                TextColor.WHITE, TextAlignmentType.NATURAL, FontWeightType.BOLD,
                null, 0L, 10L,
                10L, 20L, 20L,
                20L, 20L));
        cellList.add(createCell(CellType.TEXTCELL, text2, 10L,
                TextColor.WHITE, TextAlignmentType.NATURAL, FontWeightType.BOLD,
                null, 0L, 10L,
                10L, 20L, 20L,
                20L, 20L));
        cellList.add(createCell(CellType.TEXTCELL, text3, 10L,
                TextColor.WHITE, TextAlignmentType.NATURAL, FontWeightType.BOLD,
                null, 0L, 10L,
                10L, 20L, 20L,
                20L, 20L));

        return cellList;
    }

}