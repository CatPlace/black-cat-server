package com.spring.blackcat;

import com.spring.blackcat.address.Address;
import com.spring.blackcat.address.AddressRepository;
import com.spring.blackcat.category.Category;
import com.spring.blackcat.category.CategoryRepository;
import com.spring.blackcat.code.Role;
import com.spring.blackcat.code.TattooType;
import com.spring.blackcat.likes.LikesService;
import com.spring.blackcat.magazine.Magazine;
import com.spring.blackcat.tattoo.Tattoo;
import com.spring.blackcat.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;
    private final LikesService likesService;

    @PostConstruct
    public void init() {
        initService.initAddress();
        initService.initUser();
        initService.initCategory();
        initService.initTattoo();
        initService.initMagazine();
        likesService.likesOn(29L, "Admin1");
        likesService.likesOn(29L, "Admin2");
        likesService.likesOn(30L, "Admin3");
        likesService.likesOn(31L, "Admin1");
        likesService.likesOn(39L, "Admin1");
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;
        private final CategoryRepository categoryRepository;
        private final AddressRepository addressRepository;

        public void initAddress() {
            List<Address> addressList = new ArrayList<>();

            addressList.add(createAddress("서울"));
            addressList.add(createAddress("경기"));
            addressList.add(createAddress("인천"));
            addressList.add(createAddress("충청"));
            addressList.add(createAddress("대전"));
            addressList.add(createAddress("전라"));
            addressList.add(createAddress("광주"));
            addressList.add(createAddress("경북"));
            addressList.add(createAddress("대구"));
            addressList.add(createAddress("경남"));
            addressList.add(createAddress("부산"));
            addressList.add(createAddress("울산"));
            addressList.add(createAddress("강원"));
            addressList.add(createAddress("제주"));

            addressList.forEach(em::persist);
        }

        public void initUser() {
            List<User> userList = new ArrayList<>();

            userList.add(createUser("Admin1", "김진호", Role.ADMIN, addressRepository.findBySido("서울")));
            userList.add(createUser("Admin2", "이연중", Role.TATTOOIST, addressRepository.findBySido("대전")));
            userList.add(createUser("Admin3", "이현직", Role.BASIC, addressRepository.findBySido("대구")));

            userList.forEach(em::persist);
        }

        public void initCategory() {
            List<Category> categoryList = new ArrayList<>();

            categoryList.add(createCategory("레터링"));
            categoryList.add(createCategory("미니타투"));
            categoryList.add(createCategory("감성타투"));
            categoryList.add(createCategory("이레즈미"));
            categoryList.add(createCategory("블랙&그레이"));
            categoryList.add(createCategory("라인워크"));
            categoryList.add(createCategory("헤나"));
            categoryList.add(createCategory("커버업"));
            categoryList.add(createCategory("뉴스쿨"));
            categoryList.add(createCategory("올드스쿨"));
            categoryList.add(createCategory("잉크스플래쉬"));
            categoryList.add(createCategory("치카노"));
            categoryList.add(createCategory("컬러"));
            categoryList.add(createCategory("캐릭터"));

            categoryList.forEach(em::persist);
        }

        public void initTattoo() {
            List<Tattoo> tattooList = new ArrayList<>();

            tattooList.add(createTattoo(categoryRepository.findByName("레터링").get(), TattooType.WORK, "작품1", "설명", 10000L, 5));
            tattooList.add(createTattoo(categoryRepository.findByName("레터링").get(), TattooType.DESIGN, "도안1", "설명", 20000L, 4));
            tattooList.add(createTattoo(categoryRepository.findByName("미니타투").get(), TattooType.WORK, "작품2", "설명", 30000L, 3));
            tattooList.add(createTattoo(categoryRepository.findByName("미니타투").get(), TattooType.DESIGN, "도안2", "설명", 40000L, 2));
            tattooList.add(createTattoo(categoryRepository.findByName("감성타투").get(), TattooType.WORK, "작품3", "설명", 50000L, 1));
            tattooList.add(createTattoo(categoryRepository.findByName("감성타투").get(), TattooType.DESIGN, "도안3", "설명", 60000L, 4));
            tattooList.add(createTattoo(categoryRepository.findByName("이레즈미").get(), TattooType.WORK, "작품4", "설명", 70000L, 3));
            tattooList.add(createTattoo(categoryRepository.findByName("이레즈미").get(), TattooType.DESIGN, "도안4", "설명", 80000L, 5));
            tattooList.add(createTattoo(categoryRepository.findByName("블랙&그레이").get(), TattooType.WORK, "작품5", "설명", 90000L, 5));
            tattooList.add(createTattoo(categoryRepository.findByName("블랙&그레이").get(), TattooType.DESIGN, "도안5", "설명", 10000L, 5));

            tattooList.forEach(em::persist);
        }

        public void initMagazine() {
            List<Magazine> magazineList = new ArrayList<>();

            magazineList.add(createMagazine("타투하기 전 알아야할 것"));
            magazineList.add(createMagazine("타투 관리 방법"));
            magazineList.add(createMagazine("타투별 의미"));

            magazineList.forEach(em::persist);
        }

        private static User createUser(String id, String name, Role role, Address address) {
            User user = new User();
            user.setId(id);
            user.setName(name);
            user.setRole(role);
            user.setAddress(address);
            user.setRegisterId("Admin");
            user.setModifierId("Admin");
            return user;
        }

        private static Address createAddress(String sido) {
            Address address = new Address();
            address.setSido(sido);
            address.setRegisterId("Admin");
            address.setModifierId("Admin");
            return address;
        }

        private static Category createCategory(String name) {
            Category category = new Category();
            category.setName(name);
            category.setRegisterId("Admin");
            category.setModifierId("Admin");
            return category;
        }

        private static Tattoo createTattoo(Category category, TattooType tattooType, String name, String description, Long price, int star) {
            Tattoo tattoo = new Tattoo();
            tattoo.setCategory(category);
            tattoo.setTattooType(tattooType);
            tattoo.setName(name);
            tattoo.setDescription(description);
            tattoo.setPrice(price);
            tattoo.setStar(star);
            tattoo.setRegisterId("Admin");
            tattoo.setModifierId("Admin");
            return tattoo;
        }

        private static Magazine createMagazine(String name) {
            Magazine magazine = new Magazine();
            magazine.setName(name);
            magazine.setRegisterId("Admin");
            magazine.setModifierId("Admin");
            return magazine;
        }
    }
}
