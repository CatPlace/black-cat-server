package com.spring.blackcat.common.init;

import com.auth0.jwt.JWT;
import com.spring.blackcat.address.Address;
import com.spring.blackcat.address.AddressRepository;
import com.spring.blackcat.category.Category;
import com.spring.blackcat.category.CategoryRepository;
import com.spring.blackcat.categoryPost.CategoryPost;
import com.spring.blackcat.categoryPost.CategoryPostRepository;
import com.spring.blackcat.common.code.*;
import com.spring.blackcat.estimate.Estimate;
import com.spring.blackcat.estimate.EstimateRepository;
import com.spring.blackcat.image.Image;
import com.spring.blackcat.image.ImageRepository;
import com.spring.blackcat.likes.Likes;
import com.spring.blackcat.likes.LikesRepository;
import com.spring.blackcat.magazine.Cell;
import com.spring.blackcat.magazine.Magazine;
import com.spring.blackcat.magazine.MagazineRepository;
import com.spring.blackcat.post.Post;
import com.spring.blackcat.post.PostRepository;
import com.spring.blackcat.profile.ProfileRepository;
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
import static com.spring.blackcat.common.code.Gender.FEMALE;
import static com.spring.blackcat.common.code.Gender.MALE;
import static com.spring.blackcat.common.code.ImageType.POST;
import static com.spring.blackcat.common.code.ImageType.USER;
import static com.spring.blackcat.common.code.Role.*;
import static java.lang.Math.min;

@Slf4j
@Component
@RequiredArgsConstructor
@Profile({"LOCAL", "DEV", "PRD"})
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
    private final ProfileRepository profileRepository;
    private final EstimateRepository estimateRepository;

    private final CategoryPostRepository categoryPostRepository;

    private boolean isFirstRequest = true;

    @Transactional
    public boolean init() {
        if (isFirstRequest) {
            initAddress();
            initUser();
            initCategory();
            initTattoo();
            initMagazine();
            initLikes();
            initImage();
            initProfile();
            initEstimate();
            mapCategoryTattoo();
            isFirstRequest = false;
            return true;
        } else {
            return false;
        }
    }

    @PostConstruct
    public void initAdminUser() {
        if (userRepository.findById(1L).orElse(null) == null) {
            userRepository.save(createAdminUser());
        }
        if (userRepository.findById(2L).orElse(null) == null) {
            userRepository.save(createBasicUser());
        }
        if (userRepository.findById(3L).orElse(null) == null) {
            userRepository.save(createTattooist());
        }
        em.clear();
        createAdminJwtToken();
        createBasicJwtToken();
        createTattooistJwtToken();
    }

    public void initAddress() {
        List<Address> addressList = new ArrayList<>();

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

    public void mapCategoryTattoo() {
        List<Category> categories = categoryRepository.findAll();
        List<Tattoo> tattoos = tattooRepository.findAll();

        List<CategoryPost> categoryPostList = new ArrayList<>();

        for (int i = 0; i < min(tattoos.size(), categories.size()); i++) {
            Category category = categories.get(i);
            Tattoo tattoo = tattoos.get(i);
            CategoryPost categoryPost = new CategoryPost(tattoo, category);
            categoryPostList.add(categoryPost);
        }

        categoryPostRepository.saveAllAndFlush(categoryPostList);
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

        imageList.add(createImage("/image/url/1-1", USER, userRepository.findById(1L).orElse(null).getId()));

        imageList.add(createImage("/image/url/1-1", POST, postRepository.findById(1L).orElse(null).getId()));
        imageList.add(createImage("/image/url/1-2", POST, postRepository.findById(1L).orElse(null).getId()));
        imageList.add(createImage("/image/url/3-1", POST, postRepository.findById(3L).orElse(null).getId()));
        imageList.add(createImage("/image/url/11-1", POST, postRepository.findById(11L).orElse(null).getId()));
        imageList.add(createImage("/image/url/11-2", POST, postRepository.findById(11L).orElse(null).getId()));

        imageRepository.saveAllAndFlush(imageList);
        em.clear();
    }

    public void initProfile() {
        User user1 = userRepository.findById(1L).orElse(null);
        com.spring.blackcat.profile.Profile profile1 = new com.spring.blackcat.profile.Profile(user1);

        User user2 = userRepository.findById(2L).orElse(null);
        com.spring.blackcat.profile.Profile profile2 = new com.spring.blackcat.profile.Profile(user2);

        profileRepository.saveAndFlush(profile1);
        profileRepository.saveAndFlush(profile2);
        em.clear();
    }

    public void initEstimate() {
        User user1 = userRepository.findById(1L).orElse(null);
        Estimate estimate1 = new Estimate(user1);

        User user2 = userRepository.findById(2L).orElse(null);
        Estimate estimate2 = new Estimate(user2);

        estimateRepository.saveAndFlush(estimate1);
        estimateRepository.saveAndFlush(estimate2);
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

    private void createBasicJwtToken() {
        String token = JWT.create()
                .withSubject("BASIC")
//                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .withClaim("id", 2L)
                .sign(HMAC512(SECRET_KEY));
        log.info("BASIC TOKEN : Bearer " + token);
    }

    private void createTattooistJwtToken() {
        String token = JWT.create()
                .withSubject("TATTOOIST")
//                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .withClaim("id", 3L)
                .sign(HMAC512(SECRET_KEY));
        log.info("TATTOOIST TOKEN : Bearer " + token);
    }

    private User createAdminUser() {
        Address address = createAddress("서울");
        addressRepository.save(address);
        return new User("ADMIN", null, address, "관리자", "ADMIN",
                "admin@blackcat.pe.kr", "010-1234-5678", MALE,
                "http://openchat/admin", ADMIN, 1L, 1L);
    }

    private User createBasicUser() {
        Address address = addressRepository.findBySido("서울").orElse(null);
        return new User("BASIC", null, address, "일반사용자", "BASIC",
                "basic@blackcat.pe.kr", "010-1234-5678", FEMALE,
                "http://openchat/basic", BASIC, 1L, 1L);
    }

    private User createTattooist() {
        Address address = addressRepository.findBySido("서울").orElse(null);
        return new User("TATTOOIST", null, address, "타투이스트", "TATTOOIST",
                "tattooist@blackcat.pe.kr", "010-1234-5678", MALE,
                "http://openchat/tattooist", TATTOOIST, 1L, 1L);
    }

    private User createUser(String providerId, ProviderType providerType) {
        String defaultNickname = providerType + "_" + UUID.randomUUID();
        return new User(providerId, providerType, defaultNickname, ADMIN, 1L, 1L);
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

        return new Tattoo(name, description, price, tattooType, user);
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

    private Image createImage(String imageUrl, ImageType imageType, Long mappedId) {
        return new Image(imageUrl, imageType, mappedId);
    }
}