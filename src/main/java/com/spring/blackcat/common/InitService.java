package com.spring.blackcat.common;

import com.spring.blackcat.address.Address;
import com.spring.blackcat.address.AddressRepository;
import com.spring.blackcat.category.Category;
import com.spring.blackcat.category.CategoryRepository;
import com.spring.blackcat.code.PostType;
import com.spring.blackcat.code.Role;
import com.spring.blackcat.code.TattooType;
import com.spring.blackcat.likes.Likes;
import com.spring.blackcat.magazine.Magazine;
import com.spring.blackcat.post.Post;
import com.spring.blackcat.post.PostRepository;
import com.spring.blackcat.tattoo.Tattoo;
import com.spring.blackcat.user.User;
import com.spring.blackcat.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
@RequiredArgsConstructor
class InitService {
    private final EntityManager em;
    private final CategoryRepository categoryRepository;
    private final AddressRepository addressRepository;

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    public void initAddress() {
        List<Address> addressList = new ArrayList<>();

        addressList.add(createAddress("서울", "Seoul"));
        addressList.add(createAddress("경기", "Gyeonggi"));
        addressList.add(createAddress("인천", "Incheon"));
        addressList.add(createAddress("충청", "Chungcheong"));
        addressList.add(createAddress("대전", "Daejeon"));
        addressList.add(createAddress("전라", "Jeolla"));
        addressList.add(createAddress("광주", "Gwangju"));
        addressList.add(createAddress("경북", "Gyeongbuk"));
        addressList.add(createAddress("대구", "Daegu"));
        addressList.add(createAddress("경남", "Gyeongnam"));
        addressList.add(createAddress("부산", "Busan"));
        addressList.add(createAddress("울산", "Ulsan"));
        addressList.add(createAddress("강원", "Gangwon"));
        addressList.add(createAddress("제주", "Jeju"));

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

        tattooList.add(createTattoo(categoryRepository.findByName("레터링").orElse(null), TattooType.WORK, "작품1", "설명", 10000L, 5));
        tattooList.add(createTattoo(categoryRepository.findByName("레터링").orElse(null), TattooType.DESIGN, "도안1", "설명", 20000L, 4));
        tattooList.add(createTattoo(categoryRepository.findByName("미니타투").orElse(null), TattooType.WORK, "작품2", "설명", 30000L, 3));
        tattooList.add(createTattoo(categoryRepository.findByName("미니타투").orElse(null), TattooType.DESIGN, "도안2", "설명", 40000L, 2));
        tattooList.add(createTattoo(categoryRepository.findByName("감성타투").orElse(null), TattooType.WORK, "작품3", "설명", 50000L, 1));
        tattooList.add(createTattoo(categoryRepository.findByName("감성타투").orElse(null), TattooType.DESIGN, "도안3", "설명", 60000L, 4));
        tattooList.add(createTattoo(categoryRepository.findByName("이레즈미").orElse(null), TattooType.WORK, "작품4", "설명", 70000L, 3));
        tattooList.add(createTattoo(categoryRepository.findByName("이레즈미").orElse(null), TattooType.DESIGN, "도안4", "설명", 80000L, 5));
        tattooList.add(createTattoo(categoryRepository.findByName("블랙&그레이").orElse(null), TattooType.WORK, "작품5", "설명", 90000L, 5));
        tattooList.add(createTattoo(categoryRepository.findByName("블랙&그레이").orElse(null), TattooType.DESIGN, "도안5", "설명", 10000L, 5));
        tattooList.add(createTattoo(categoryRepository.findByName("레터링").orElse(null), TattooType.WORK, "작품1", "설명", 10000L, 5));
        tattooList.add(createTattoo(categoryRepository.findByName("레터링").orElse(null), TattooType.DESIGN, "도안1", "설명", 20000L, 4));
        tattooList.add(createTattoo(categoryRepository.findByName("미니타투").orElse(null), TattooType.WORK, "작품2", "설명", 30000L, 3));
        tattooList.add(createTattoo(categoryRepository.findByName("미니타투").orElse(null), TattooType.DESIGN, "도안2", "설명", 40000L, 2));
        tattooList.add(createTattoo(categoryRepository.findByName("감성타투").orElse(null), TattooType.WORK, "작품3", "설명", 50000L, 1));
        tattooList.add(createTattoo(categoryRepository.findByName("감성타투").orElse(null), TattooType.DESIGN, "도안3", "설명", 60000L, 4));
        tattooList.add(createTattoo(categoryRepository.findByName("이레즈미").orElse(null), TattooType.WORK, "작품4", "설명", 70000L, 3));
        tattooList.add(createTattoo(categoryRepository.findByName("이레즈미").orElse(null), TattooType.DESIGN, "도안4", "설명", 80000L, 5));
        tattooList.add(createTattoo(categoryRepository.findByName("블랙&그레이").orElse(null), TattooType.WORK, "작품5", "설명", 90000L, 5));
        tattooList.add(createTattoo(categoryRepository.findByName("블랙&그레이").orElse(null), TattooType.DESIGN, "도안5", "설명", 10000L, 5));

        tattooList.forEach(em::persist);
    }

    public void initMagazine() {
        List<Magazine> magazineList = new ArrayList<>();

        magazineList.add(createMagazine("타투하기 전 알아야할 것"));
        magazineList.add(createMagazine("타투 관리 방법"));
        magazineList.add(createMagazine("타투별 의미"));

        magazineList.forEach(em::persist);
    }

    public void initLikes() {
        List<Post> postList = new ArrayList<>();
        List<User> userList = new ArrayList<>();
        List<Likes> likesList = new ArrayList<>();

        postList.add(postRepository.findById(29L).orElse(null));
        postList.add(postRepository.findById(29L).orElse(null));
        postList.add(postRepository.findById(30L).orElse(null));
        postList.add(postRepository.findById(31L).orElse(null));
        postList.add(postRepository.findById(39L).orElse(null));

        userList.add(userRepository.findById("Admin1").orElse(null));
        userList.add(userRepository.findById("Admin2").orElse(null));
        userList.add(userRepository.findById("Admin3").orElse(null));
        userList.add(userRepository.findById("Admin1").orElse(null));
        userList.add(userRepository.findById("Admin1").orElse(null));

        for (int i = 0; i < postList.size(); i++) {
            likesList.add(createLikes(postList.get(i), userList.get(i), postList.get(i).getPostTypeCd()));
        }

        likesList.forEach(em::persist);
    }

    private static User createUser(String id, String name, Role role, Address address) {
        return new User(id, name, role, address, "Admin", "Admin");
    }

    private static Address createAddress(String sido, String sidoEn) {
        return new Address(sido, sidoEn, "Admin", "Admin");
    }

    private static Category createCategory(String name) {
        return new Category(name, "Admin", "Admin");
    }

    private static Tattoo createTattoo(Category category, TattooType tattooType, String name, String description, Long price, int star) {
        return new Tattoo(name, description, price, star, category, tattooType, "Admin", "Admin");
    }

    private static Magazine createMagazine(String name) {
        return new Magazine(name, "Admin", "Admin");
    }

    private static Likes createLikes(Post post, User user, PostType postType) {
        return new Likes(post, user, postType, "Admin", "Admin");
    }
}