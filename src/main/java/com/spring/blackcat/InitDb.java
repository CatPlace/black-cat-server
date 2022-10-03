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
            Address address1 = createAddress("서울");
            Address address2 = createAddress("경기");
            Address address3 = createAddress("인천");
            Address address4 = createAddress("충청");
            Address address5 = createAddress("대전");
            Address address6 = createAddress("전라");
            Address address7 = createAddress("광주");
            Address address8 = createAddress("경북");
            Address address9 = createAddress("대구");
            Address address10 = createAddress("경남");
            Address address11 = createAddress("부산");
            Address address12 = createAddress("울산");
            Address address13 = createAddress("강원");
            Address address14 = createAddress("제주");
            em.persist(address1);
            em.persist(address2);
            em.persist(address3);
            em.persist(address4);
            em.persist(address5);
            em.persist(address6);
            em.persist(address7);
            em.persist(address8);
            em.persist(address9);
            em.persist(address10);
            em.persist(address11);
            em.persist(address12);
            em.persist(address13);
            em.persist(address14);
        }

        public void initUser() {
            User user1 = createUser("Admin1", "김진호", Role.ADMIN, addressRepository.findBySido("서울"));
            User user2 = createUser("Admin2", "이연중", Role.TATTOOIST, addressRepository.findBySido("대전"));
            User user3 = createUser("Admin3", "이현직", Role.BASIC, addressRepository.findBySido("대구"));
            em.persist(user1);
            em.persist(user2);
            em.persist(user3);
        }

        public void initCategory() {
            Category category1 = createCategory("레터링");
            Category category2 = createCategory("미니타투");
            Category category3 = createCategory("감성타투");
            Category category4 = createCategory("이레즈미");
            Category category5 = createCategory("블랙&그레이");
            Category category6 = createCategory("라인워크");
            Category category7 = createCategory("헤나");
            Category category8 = createCategory("커버업");
            Category category9 = createCategory("뉴스쿨");
            Category category10 = createCategory("올드스쿨");
            Category category11 = createCategory("잉크스플래쉬");
            Category category12 = createCategory("치카노");
            Category category13 = createCategory("컬러");
            Category category14 = createCategory("캐릭터");
            em.persist(category1);
            em.persist(category2);
            em.persist(category3);
            em.persist(category4);
            em.persist(category5);
            em.persist(category6);
            em.persist(category7);
            em.persist(category8);
            em.persist(category9);
            em.persist(category10);
            em.persist(category11);
            em.persist(category12);
            em.persist(category13);
            em.persist(category14);
        }

        public void initTattoo() {
            Tattoo tattoo1 = createTattoo(categoryRepository.findByName("레터링").get(), TattooType.WORK, "작품1");
            Tattoo tattoo2 = createTattoo(categoryRepository.findByName("레터링").get(), TattooType.DESIGN, "도안1");
            Tattoo tattoo3 = createTattoo(categoryRepository.findByName("미니타투").get(), TattooType.WORK, "작품2");
            Tattoo tattoo4 = createTattoo(categoryRepository.findByName("미니타투").get(), TattooType.DESIGN, "도안2");
            Tattoo tattoo5 = createTattoo(categoryRepository.findByName("감성타투").get(), TattooType.WORK, "작품3");
            Tattoo tattoo6 = createTattoo(categoryRepository.findByName("감성타투").get(), TattooType.DESIGN, "도안3");
            Tattoo tattoo7 = createTattoo(categoryRepository.findByName("이레즈미").get(), TattooType.WORK, "작품4");
            Tattoo tattoo8 = createTattoo(categoryRepository.findByName("이레즈미").get(), TattooType.DESIGN, "도안4");
            Tattoo tattoo9 = createTattoo(categoryRepository.findByName("블랙&그레이").get(), TattooType.WORK, "작품5");
            Tattoo tattoo10 = createTattoo(categoryRepository.findByName("블랙&그레이").get(), TattooType.DESIGN, "도안5");
            em.persist(tattoo1);
            em.persist(tattoo2);
            em.persist(tattoo3);
            em.persist(tattoo4);
            em.persist(tattoo5);
            em.persist(tattoo6);
            em.persist(tattoo7);
            em.persist(tattoo8);
            em.persist(tattoo9);
            em.persist(tattoo10);
        }

        public void initMagazine() {
            Magazine magazine1 = createMagazine("타투하기 전 알아야할 것");
            Magazine magazine2 = createMagazine("타투 관리 방법");
            Magazine magazine3 = createMagazine("타투별 의미");
            em.persist(magazine1);
            em.persist(magazine2);
            em.persist(magazine3);
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

        private static Tattoo createTattoo(Category category, TattooType tattooType, String name) {
            Tattoo tattoo = new Tattoo();
            tattoo.setCategory(category);
            tattoo.setTattooType(tattooType);
            tattoo.setName(name);
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
