package com.spring.blackcat.tattoo;

import com.spring.blackcat.address.Address;
import com.spring.blackcat.address.AddressRepository;
import com.spring.blackcat.category.Category;
import com.spring.blackcat.category.CategoryRepository;
import com.spring.blackcat.common.code.Role;
import com.spring.blackcat.common.code.TattooType;
import com.spring.blackcat.tattoo.dto.GetTattoosResDto;
import com.spring.blackcat.user.User;
import com.spring.blackcat.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.transaction.Transactional;
import java.util.ArrayList;
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
    UserRepository userRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    TattooService tattooService;

    @Test
    @DisplayName("쿼리 스트링 없을 때 특정 카테고리 타투 조회")
    void getTattoosByCategoryIdNoQueryString() {
        //given
        String userName = "Admin1";
        //default pageable object
        PageRequest pageRequest = PageRequest.of(0, 20, Sort.Direction.DESC, "id");
        this.Insert();
        Long categoryId = this.categoryRepository.findByName("감성타투").get().getId();


        //when
        Page<GetTattoosResDto> allTattoos = tattooService.getTattoosByCategoryId(pageRequest, userName, categoryId);

        //then
        assertThat(allTattoos.getNumber()).isEqualTo(0);
        assertThat(allTattoos.getNumberOfElements()).isEqualTo(2);
    }

    @Test
    @DisplayName("쿼리 스트링 페이지만 넣었을 때 특정 카테고리 타투 조회")
    void getTattoosByCategoryIdOnlyPageQueryString() {
        //given
        String userName = "Admin1";
        //default pageable object
        PageRequest pageRequest = PageRequest.of(1, 20, Sort.Direction.DESC, "id");
        this.Insert();
        Long categoryId = this.categoryRepository.findByName("감성타투").get().getId();


        //when
        Page<GetTattoosResDto> allTattoos = tattooService.getTattoosByCategoryId(pageRequest, userName, categoryId);

        //then
        assertThat(allTattoos.getNumber()).isEqualTo(1);
        assertThat(allTattoos.getNumberOfElements()).isEqualTo(0);
    }

    @Test
    @DisplayName("쿼리 스트링 사이즈만 넣었을 때 특정 카테고리 타투 조회")
    void getTattoosByCategoryIdOnlySizeQueryString() {
        //given
        String userName = "Admin1";
        //default pageable object
        PageRequest pageRequest = PageRequest.of(0, 2, Sort.Direction.DESC, "id");
        this.Insert();
        Long categoryId = this.categoryRepository.findByName("감성타투").get().getId();

        //when
        Page<GetTattoosResDto> allTattoos = tattooService.getTattoosByCategoryId(pageRequest, userName, categoryId);

        //then
        assertThat(allTattoos.getNumber()).isEqualTo(0);
        assertThat(allTattoos.getNumberOfElements()).isEqualTo(2);
    }

    @Test
    @DisplayName("쿼리 스트링 페이지, 사이즈만 넣었을 때 특정 카테고리 타투 조회")
    void getTattoosByCategoryIdOnlyPageAndSizeQueryString() {
        //given
        String userName = "Admin1";
        //default pageable object
        PageRequest pageRequest = PageRequest.of(1, 2, Sort.Direction.DESC, "id");
        this.Insert();
        Long categoryId = this.categoryRepository.findByName("감성타투").get().getId();

        //when
        Page<GetTattoosResDto> allTattoos = tattooService.getTattoosByCategoryId(pageRequest, userName, categoryId);

        //then
        assertThat(allTattoos.getNumber()).isEqualTo(1);
        assertThat(allTattoos.getNumberOfElements()).isEqualTo(0);
    }

    @Test
    @DisplayName("쿼리 스트링 모두 넣었을 때 특정 카테고리 타투 조회")
    void getTattoosByCategoryIdAllQueryString() {
        //given
        String userName = "Admin1";
        //default pageable object
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.Direction.ASC, "price");
        this.Insert();
        Long categoryId = this.categoryRepository.findByName("감성타투").get().getId();
        Tattoo tattoo = new Tattoo("작품1", "설명근", 0L, categoryRepository.findById(categoryId).orElse(null), TattooType.DESIGN, userName, userName);
        tattooRepository.save(tattoo);

        //when
        Page<GetTattoosResDto> allTattoos = tattooService.getTattoosByCategoryId(pageRequest, userName, categoryId);

        //then
        assertThat(allTattoos.getNumber()).isEqualTo(0);
        assertThat(allTattoos.getNumberOfElements()).isEqualTo(3);
        assertThat(allTattoos.getContent().get(0).getPrice()).isEqualTo(0);
    }

    private void Insert() {
        String userName = "Admin1";

        Address address = new Address("서울", "Seoul", userName, userName);
        User user = new User(userName, userName, Role.ADMIN, address, userName, userName);
        Category category1 = new Category("감성타투", userName, userName);
        Category category2 = new Category("안감성타투", userName, userName);

        List<Tattoo> tattoos = new ArrayList<>();
        tattoos.add(new Tattoo("타투1", "설명근", 10000L, category1, TattooType.DESIGN, userName, userName));
        tattoos.add(new Tattoo("타투2", "설명근", 20000L, category1, TattooType.DESIGN, userName, userName));
        tattoos.add(new Tattoo("타투3", "설명근", 30000L, category2, TattooType.DESIGN, userName, userName));
        tattoos.add(new Tattoo("타투4", "설명근", 40000L, category2, TattooType.DESIGN, userName, userName));

        addressRepository.save(address);
        userRepository.save(user);
        categoryRepository.save(category1);
        categoryRepository.save(category2);
        tattooRepository.saveAll(tattoos);
    }
}
