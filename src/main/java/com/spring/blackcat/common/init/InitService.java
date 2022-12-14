package com.spring.blackcat.common.init;

import com.auth0.jwt.JWT;
import com.spring.blackcat.address.Address;
import com.spring.blackcat.address.AddressRepository;
import com.spring.blackcat.category.Category;
import com.spring.blackcat.category.CategoryRepository;
import com.spring.blackcat.common.code.*;
import com.spring.blackcat.image.Image;
import com.spring.blackcat.image.ImageRepository;
import com.spring.blackcat.likes.Likes;
import com.spring.blackcat.likes.LikesRepository;
import com.spring.blackcat.magazine.Cell;
import com.spring.blackcat.magazine.Magazine;
import com.spring.blackcat.magazine.MagazineRepository;
import com.spring.blackcat.post.Post;
import com.spring.blackcat.post.PostRepository;
import com.spring.blackcat.tattoo.Tattoo;
import com.spring.blackcat.tattoo.TattooRepository;
import com.spring.blackcat.user.User;
import com.spring.blackcat.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
@Profile({"LOCAL", "DEV", "PRD"}) // 테스트 환경 실행 X (spring.profiles.active)
class InitService {

    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    private final EntityManager em;

    private final PasswordEncoder bCryptPasswordEncoder;

    private final AddressRepository addressRepository;

    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;

    private final PostRepository postRepository;

    private final TattooRepository tattooRepository;

    private final MagazineRepository magazineRepository;

    private final LikesRepository likesRepository;

    private final ImageRepository imageRepository;

    @PostConstruct
    public void initAdminUser() {
        if (userRepository.findById(1L).orElse(null) == null) {
            userRepository.save(createAdminUser());
        }
        if (userRepository.findById(2L).orElse(null) == null) {
            userRepository.save(createTestUser());
        }
        em.clear();
        createAdminJwtToken();
        createTestJwtToken();
    }

    public void initAddress() {
        List<Address> addressList = new ArrayList<>();

//        addressList.add(new Address("07281", "서울특별시", "Seoul", "영등포구", "Yeongdeungpo-gu", "", "", "115604154433", "선유로13길", "Seonyu-ro 13-gil", "0", "5", "0", "1156012400100020002037439", "", "문래동 현대홈시티2", "1156012400", "문래동6가", "", "문래동", "0", "2", "01", "2", "", "", 1L, 1L));
//        addressList.add(new Address("07282", "서울특별시", "Seoul", "영등포구", "Yeongdeungpo-gu", "", "", "115604154461", "선유로9길", "Seonyu-ro 9-gil", "0", "30", "0", "1156012400100210000000005", "", "문래롯데캐슬", "1156012400", "문래동6가", "", "문래동", "0", "57", "02", "0", "", "", 1L, 1L));

//        addressList.add(createAddress("서울"));
        addressList.add(createAddress("경기"));
        addressList.add(createAddress("인천"));
        addressList.add(createAddress("충청/대전"));
        addressList.add(createAddress("전라/광주"));
        addressList.add(createAddress("경북/대구"));
        addressList.add(createAddress("경남/부산/울산"));
        addressList.add(createAddress("강원"));
        addressList.add(createAddress("제주"));

        addressRepository.saveAllAndFlush(addressList);
        em.clear();
    }

    public void initUser() {
        List<User> userList = new ArrayList<>();

        userList.add(createUser("123456789", ProviderType.KAKAO));
        userList.add(createUser("asdqweqwr", ProviderType.APPLE));
        userList.add(createUser("!@#!@!$%!@eqw", ProviderType.KAKAO));

        userRepository.saveAllAndFlush(userList);
        em.clear();
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

        categoryRepository.saveAllAndFlush(categoryList);
        em.clear();
    }

    public void initTattoo() {
        List<Tattoo> tattooList = new ArrayList<>();

        tattooList.add(createTattoo(categoryRepository.findByName("레터링").orElse(null), TattooType.WORK, "작품1", "설명", 10000L));
        tattooList.add(createTattoo(categoryRepository.findByName("레터링").orElse(null), TattooType.DESIGN, "도안1", "설명", 20000L));
        tattooList.add(createTattoo(categoryRepository.findByName("미니타투").orElse(null), TattooType.WORK, "작품2", "설명", 30000L));
        tattooList.add(createTattoo(categoryRepository.findByName("미니타투").orElse(null), TattooType.DESIGN, "도안2", "설명", 40000L));
        tattooList.add(createTattoo(categoryRepository.findByName("감성타투").orElse(null), TattooType.WORK, "작품3", "설명", 50000L));
        tattooList.add(createTattoo(categoryRepository.findByName("감성타투").orElse(null), TattooType.DESIGN, "도안3", "설명", 60000L));
        tattooList.add(createTattoo(categoryRepository.findByName("이레즈미").orElse(null), TattooType.WORK, "작품4", "설명", 70000L));
        tattooList.add(createTattoo(categoryRepository.findByName("이레즈미").orElse(null), TattooType.DESIGN, "도안4", "설명", 80000L));
        tattooList.add(createTattoo(categoryRepository.findByName("블랙&그레이").orElse(null), TattooType.WORK, "작품5", "설명", 90000L));
        tattooList.add(createTattoo(categoryRepository.findByName("블랙&그레이").orElse(null), TattooType.DESIGN, "도안5", "설명", 10000L));

        tattooRepository.saveAllAndFlush(tattooList);
        em.clear();
    }

    public void initMagazine() {
        List<Magazine> magazineList = new ArrayList<>();
        List<Cell> cellList1 = createCells("타투 말은 많이 들어보고 주변에서도 요즘 많이 하던데 뭐가 뭔지 잘 모르겠어",
                "사람마다 모양, 색깔도 다르던데... 그리고 그거 아픈거 아냐?",
                "하는 당신을 위해 타투이스트 깜냥이가 모두 답변해드리겠습니닷!");

        List<Cell> cellList2 = createCells("2010년 연구에 따르면 18세에서 29세 사이의 사람들 중 무려 38%가 일생에 한 번 이상 타투를 해본 경험이 있다고 합니다.",
                "약 10년이 넘게 지난 지금 타투에 관한 인식이 이전보다 많이 좋아졌기 때문에 훨씬 더 많은 사람이 타투를 해본 경험이 있을 것입니다.",
                "하지만 처음 타투를 하기로 다짐했다면 '타투를 하면 아프나요?'가 자연스러운 질문일 것입니다.");

        magazineList.add(createMagazine("타린이들을 위한 안내서"));
        magazineList.add(createMagazine("타투를 하면 얼마나 아픈가요?"));

        cellList1.forEach(cell -> cell.changeMagazine(magazineList.get(0)));
        cellList2.forEach(cell -> cell.changeMagazine(magazineList.get(1)));

        magazineRepository.saveAllAndFlush(magazineList);
        em.clear();
    }

    public void initLikes() {
        List<Post> postList = new ArrayList<>();
        List<User> userList = new ArrayList<>();
        List<Likes> likesList = new ArrayList<>();

        postList.add(postRepository.findById(1L).orElse(null));
        postList.add(postRepository.findById(1L).orElse(null));
        postList.add(postRepository.findById(2L).orElse(null));
        postList.add(postRepository.findById(3L).orElse(null));
        postList.add(postRepository.findById(11L).orElse(null));

        userList.add(userRepository.findById(1L).orElse(null));
        userList.add(userRepository.findById(2L).orElse(null));
        userList.add(userRepository.findById(3L).orElse(null));
        userList.add(userRepository.findById(1L).orElse(null));
        userList.add(userRepository.findById(1L).orElse(null));

        for (int i = 0; i < postList.size(); i++) {
            Post post = postList.get(i);
            User user = userList.get(i);
            PostType postType = post.getPostType();
            likesList.add(createLikes(post, user, postType));
        }

        likesRepository.saveAllAndFlush(likesList);
        em.clear();
    }

    public void initImage() {
        List<Image> imageList = new ArrayList<>();

        imageList.add(createImage(postRepository.findById(1L).orElse(null), "/image/url/1"));
        imageList.add(createImage(postRepository.findById(3L).orElse(null), "/image/url/2"));
        imageList.add(createImage(postRepository.findById(11L).orElse(null), "/image/url/3"));

        imageRepository.saveAllAndFlush(imageList);
        em.clear();
    }

    private void createAdminJwtToken() {
        String token = JWT.create()
                .withSubject("ADMIN")
//                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .withClaim("id", 1L)
                .sign(HMAC512(SECRET_KEY));

        log.info("ADMIN TOKEN : Bearer " + token);
    }

    private void createTestJwtToken() {
        String token = JWT.create()
                .withSubject("TEST")
//                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .withClaim("id", 2L)
                .sign(HMAC512(SECRET_KEY));

        log.info("TEST TOKEN : Bearer " + token);
    }

    private User createAdminUser() {
        Address address = createAddress("서울");
        addressRepository.save(address);
        return new User("ADMIN", null, address, "ADMIN", Role.ADMIN, 1L, 1L);
    }

    private User createTestUser() {
        return new User("TEST", null, null, "TEST", Role.BASIC, 1L, 1L);
    }

    private User createUser(String providerId, ProviderType providerType) {
        String defaultNickname = providerType + "_" + UUID.randomUUID();
        return new User(providerId, providerType, defaultNickname, Role.ADMIN, 1L, 1L);
    }

    private Address createAddress(String sido) {
        return new Address(sido, 1L, 1L);
    }

    private Category createCategory(String name) {
        return new Category(name, 1L, 1L);
    }

    private Tattoo createTattoo(Category category, TattooType tattooType, String name, String description, Long price) {
        Address address = addressRepository.findById(1L).get();
        User user = this.userRepository.findById(1L).get();

        return new Tattoo(name, description, price, category, tattooType, user);
    }

    private Magazine createMagazine(String title) {
        Address address = addressRepository.findById(1L).get();
        User user = this.userRepository.findById(1L).get();

        return new Magazine(title, user);
    }

    private Likes createLikes(Post post, User user, PostType postType) {
        return new Likes(post, user, postType);
    }

    private Cell createCell(
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

    private List<Cell> createCells(String text1, String text2, String text3) {
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

    private Image createImage(Post post, String imageUrl) {
        return new Image(post, imageUrl);
    }
}