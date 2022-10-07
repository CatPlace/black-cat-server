package com.spring.blackcat.tattoo;

import com.spring.blackcat.category.CategoryRepository;
import com.spring.blackcat.code.TattooType;
import com.spring.blackcat.tattoo.dto.GetTattoosResDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class GetTattoosByCategoryIdTest {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    TattooRepository tattooRepository;
    @Autowired
    TattooService tattooService;

    @Test
    @DisplayName("쿼리 스트링 없을 때 특정 카테고리 타투 조회")
    void getTattoosByCategoryIdNoQueryString() {
        //given
        String userName = "Admin1";
        //default pageable object
        PageRequest pageRequest = PageRequest.of(0, 20, Sort.Direction.DESC, "id");
        Long categoryId = 15L;

        //when
        List<GetTattoosResDto> tattoos = tattooService.getTattoosByCategoryId(pageRequest, userName, categoryId);

        //then
        assertThat(tattoos.size()).isEqualTo(4);
    }

    @Test
    @DisplayName("쿼리 스트링 페이지만 넣었을 때 특정 카테고리 타투 조회")
    void getTattoosByCategoryIdOnlyPageQueryString() {
        //given
        String userName = "Admin1";
        //default pageable object
        PageRequest pageRequest = PageRequest.of(1, 20, Sort.Direction.DESC, "id");
        Long categoryId = 15L;

        //when
        List<GetTattoosResDto> tattoos = tattooService.getTattoosByCategoryId(pageRequest, userName, categoryId);

        //then
        //기본 20개 씩 조회 시, 두 번째 페이지에는 데이터가 없음(총 데이터 20개)
        assertThat(tattoos.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("쿼리 스트링 사이즈만 넣었을 때 특정 카테고리 타투 조회")
    void getTattoosByCategoryIdOnlySizeQueryString() {
        //given
        String userName = "Admin1";
        //default pageable object
        PageRequest pageRequest = PageRequest.of(0, 2, Sort.Direction.DESC, "id");
        Long categoryId = 15L;

        //when
        List<GetTattoosResDto> tattoos = tattooService.getTattoosByCategoryId(pageRequest, userName, categoryId);

        //then
        assertThat(tattoos.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("쿼리 스트링 페이지, 사이즈만 넣었을 때 특정 카테고리 타투 조회")
    void getTattoosByCategoryIdOnlyPageAndSizeQueryString() {
        //given
        String userName = "Admin1";
        //default pageable object
        PageRequest pageRequest = PageRequest.of(1, 2, Sort.Direction.DESC, "id");
        Long categoryId = 15L;

        //when
        List<GetTattoosResDto> tattoos = tattooService.getTattoosByCategoryId(pageRequest, userName, categoryId);

        //then
        assertThat(tattoos.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("쿼리 스트링 모두 넣었을 때 특정 카테고리 타투 조회")
    void getTattoosByCategoryIdAllQueryString() {
        //given
        String userName = "Admin1";
        //default pageable object
        PageRequest pageRequest = PageRequest.of(0, 21, Sort.Direction.ASC, "price");
        Long categoryId = 15L;

        Tattoo tattoo = new Tattoo("작품1", "설명근", 0L, 5, categoryRepository.findByName("레터링").orElse(null), TattooType.DESIGN, userName, userName);
        tattooRepository.save(tattoo);

        //when
        List<GetTattoosResDto> tattoos = tattooService.getTattoosByCategoryId(pageRequest, userName, categoryId);

        //then
        assertThat(tattoos.size()).isEqualTo(5);
        assertThat(tattoos.get(0).getPrice()).isEqualTo(0);
    }

    //InitDB 없애고 사용할 예정(현재 테스트 코드가 InitDB 데이터에 의존적임)
//    private Long insertAndReturnCategoryId() {
//        String userName = "Admin1";
//
//        Category category = new Category("카테고리1", userName, userName);
//        categoryRepository.save(category);
//
//        Tattoo tattoo1 = new Tattoo("타투1", "설명근", 10000L, 4, category, TattooType.DESIGN, userName, userName);
//        Tattoo tattoo2 = new Tattoo("타투2", "설명근", 20000L, 4, category, TattooType.DESIGN, userName, userName);
//        Tattoo tattoo3 = new Tattoo("타투2", "설명근", 20000L, 4, category, TattooType.DESIGN, userName, userName);
//        Tattoo tattoo4 = new Tattoo("타투2", "설명근", 20000L, 4, category, TattooType.DESIGN, userName, userName);
//        Tattoo tattoo5 = new Tattoo("타투2", "설명근", 20000L, 4, category, TattooType.DESIGN, userName, userName);
//        tattooRepository.save(tattoo1);
//        tattooRepository.save(tattoo2);
//        tattooRepository.save(tattoo3);
//        tattooRepository.save(tattoo4);
//        tattooRepository.save(tattoo5);
//
//        return category.getId();
//    }
}
