//package com.spring.blackcat.tattoo;
//
//import com.spring.blackcat.address.Address;
//import com.spring.blackcat.address.AddressRepository;
//import com.spring.blackcat.category.Category;
//import com.spring.blackcat.category.CategoryRepository;
//import com.spring.blackcat.common.code.Role;
//import com.spring.blackcat.common.code.TattooType;
//import com.spring.blackcat.tattoo.dto.GetTattoosResDto;
//import com.spring.blackcat.user.User;
//import com.spring.blackcat.user.UserRepository;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort;
//
//import javax.transaction.Transactional;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//@Transactional
//public class GetAllTattoosTest {
//    @Autowired
//    CategoryRepository categoryRepository;
//    @Autowired
//    TattooRepository tattooRepository;
//    @Autowired
//    UserRepository userRepository;
//    @Autowired
//    AddressRepository addressRepository;
//    @Autowired
//    TattooService tattooService;
//
//    @Test
//    @DisplayName("쿼리 스트링 없을 때 전체 타투 조회")
//    void getAllTattoosNoQueryString() {
//        //given
//        Long userId = 1L;
//        //default pageable object
//        PageRequest pageRequest = PageRequest.of(0, 20, Sort.Direction.DESC, "id");
//        this.Insert();
//
//        //when
//        Page<GetTattoosResDto> allTattoos = tattooService.getAllTattoos(pageRequest, userId);
//
//        //then
//        assertThat(allTattoos.getNumber()).isEqualTo(0);
//        assertThat(allTattoos.getNumberOfElements()).isEqualTo(4);
//    }
//
//    @Test
//    @DisplayName("쿼리 스트링 페이지만 넣었을 때 전체 타투 조회")
//    void getAllTattoosOnlyPageQueryString() {
//        //given
//        Long userId = 1L;
//        //default pageable object
//        PageRequest pageRequest = PageRequest.of(1, 20, Sort.Direction.DESC, "id");
//
//
//        //when
//        Page<GetTattoosResDto> allTattoos = tattooService.getAllTattoos(pageRequest, userId);
//
//        //then
//        assertThat(allTattoos.getNumber()).isEqualTo(1);
//        assertThat(allTattoos.getNumberOfElements()).isEqualTo(0);
//    }
//
//    @Test
//    @DisplayName("쿼리 스트링 사이즈만 넣었을 때 전체 타투 조회")
//    void getAllTattoosOnlySizeQueryString() {
//        //given
//        Long userId = 1L;
//        //default pageable object
//        PageRequest pageRequest = PageRequest.of(0, 2, Sort.Direction.DESC, "id");
//        this.Insert();
//
//        //when
//        Page<GetTattoosResDto> allTattoos = tattooService.getAllTattoos(pageRequest, userId);
//
//        //then
//        assertThat(allTattoos.getNumber()).isEqualTo(0);
//        assertThat(allTattoos.getNumberOfElements()).isEqualTo(2);
//    }
//
//    @Test
//    @DisplayName("쿼리 스트링 페이지, 사이즈만 넣었을 때 전체 타투 조회")
//    void getAllTattoosOnlyPageAndSizeQueryString() {
//        //given
//        Long userId = 1L;
//        //default pageable object
//        PageRequest pageRequest = PageRequest.of(1, 2, Sort.Direction.DESC, "id");
//        this.Insert();
//
//        //when
//        Page<GetTattoosResDto> allTattoos = tattooService.getAllTattoos(pageRequest, userId);
//
//        //then
//        assertThat(allTattoos.getNumber()).isEqualTo(1);
//        assertThat(allTattoos.getNumberOfElements()).isEqualTo(2);
//    }
//
//    @Test
//    @DisplayName("쿼리 스트링 모두 넣었을 때 전체 타투 조회")
//    void getAllTattoosAllQueryString() {
//        //given
//        Long userId = 1L;
//        //default pageable object
//        PageRequest pageRequest = PageRequest.of(0, 5, Sort.Direction.ASC, "price");
//        this.Insert();
//        Tattoo tattoo = new Tattoo("작품5", "설명근", 0L, categoryRepository.findByName("감성타투").orElse(null), TattooType.DESIGN, userId, userId);
//        tattooRepository.save(tattoo);
//
//        //when
//        Page<GetTattoosResDto> allTattoos = tattooService.getAllTattoos(pageRequest, userId);
//
//        //then
//        assertThat(allTattoos.getNumber()).isEqualTo(0);
//        assertThat(allTattoos.getNumberOfElements()).isEqualTo(5);
//        assertThat(allTattoos.getContent().get(0).getPrice()).isEqualTo(0);
//    }
//
//    private void Insert() {
//        Long userId = 1L;
//        String userName = "TEST";
//
//        Address address = new Address("서울", "Seoul", userId, userId);
//        User user = new User(userName, null, address, userName, Role.ADMIN, userId, userId);
//        Category category = new Category("감성타투", userId, userId);
//
//        List<Tattoo> tattoos = new ArrayList<>();
//        tattoos.add(new Tattoo("타투1", "설명근", 10000L, category, TattooType.DESIGN, userId, userId));
//        tattoos.add(new Tattoo("타투2", "설명근", 20000L, category, TattooType.DESIGN, userId, userId));
//        tattoos.add(new Tattoo("타투3", "설명근", 30000L, category, TattooType.DESIGN, userId, userId));
//        tattoos.add(new Tattoo("타투4", "설명근", 4000L, category, TattooType.DESIGN, userId, userId));
//
//        addressRepository.save(address);
//        userRepository.save(user);
//        categoryRepository.save(category);
//        tattooRepository.saveAll(tattoos);
//    }
//}
