package com.spring.blackcat.tattoo;

import com.spring.blackcat.category.CategoryRepository;
import com.spring.blackcat.code.TattooType;
import com.spring.blackcat.tattoo.dto.GetAllTattoosResDto;
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
public class GetAllTattoosTest {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    TattooRepository tattooRepository;
    @Autowired
    TattooService tattooService;

    @Test
    @DisplayName("쿼리 스트링 없을 때 전체 타투 조회")
    void getAllTattoosNoQueryString() {
        //given
        String userName = "Admin1";
        //default pageable object
        PageRequest pageRequest = PageRequest.of(0, 20, Sort.Direction.DESC, "id");

        //when
        List<GetAllTattoosResDto> allTattoos = tattooService.getAllTattoos(pageRequest, userName);

        //then
        assertThat(allTattoos.size()).isEqualTo(20);
    }

    @Test
    @DisplayName("쿼리 스트링 페이지만 넣었을 때 전체 타투 조회")
    void getAllTattoosOnlyPageQueryString() {
        //given
        String userName = "Admin1";
        //default pageable object
        PageRequest pageRequest = PageRequest.of(1, 20, Sort.Direction.DESC, "id");

        //when
        List<GetAllTattoosResDto> allTattoos = tattooService.getAllTattoos(pageRequest, userName);

        //then
        //기본 20개 씩 조회 시, 두 번째 페이지에는 데이터가 없음(총 데이터 20개)
        assertThat(allTattoos.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("쿼리 스트링 사이즈만 넣었을 때 전체 타투 조회")
    void getAllTattoosOnlySizeQueryString() {
        //given
        String userName = "Admin1";
        //default pageable object
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.Direction.DESC, "id");

        //when
        List<GetAllTattoosResDto> allTattoos = tattooService.getAllTattoos(pageRequest, userName);

        //then
        assertThat(allTattoos.size()).isEqualTo(10);
    }

    @Test
    @DisplayName("쿼리 스트링 페이지, 사이즈만 넣었을 때 전체 타투 조회")
    void getAllTattoosOnlyPageAndSizeQueryString() {
        //given
        String userName = "Admin1";
        //default pageable object
        PageRequest pageRequest = PageRequest.of(1, 5, Sort.Direction.DESC, "id");

        //when
        List<GetAllTattoosResDto> allTattoos = tattooService.getAllTattoos(pageRequest, userName);

        //then
        assertThat(allTattoos.size()).isEqualTo(5);
    }

    @Test
    @DisplayName("쿼리 스트링 모두 넣었을 때 전체 타투 조회")
    void getAllTattoosAllQueryString() {
        //given
        String userName = "Admin1";
        //default pageable object
        PageRequest pageRequest = PageRequest.of(0, 21, Sort.Direction.ASC, "price");
        Tattoo tattoo = new Tattoo("작품1", "설명근", 0L, 5, categoryRepository.findByName("이레즈미").orElse(null), TattooType.DESIGN, userName, userName);
        tattooRepository.save(tattoo);

        //when
        List<GetAllTattoosResDto> allTattoos = tattooService.getAllTattoos(pageRequest, userName);

        //then
        assertThat(allTattoos.size()).isEqualTo(21);
        assertThat(allTattoos.get(0).getPrice()).isEqualTo(0);
    }
}
