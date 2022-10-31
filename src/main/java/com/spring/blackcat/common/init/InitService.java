package com.spring.blackcat.common.init;

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
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private final PasswordEncoder bCryptPasswordEncoder;

    private final AddressRepository addressRepository;

    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;

    private final PostRepository postRepository;

    private final TattooRepository tattooRepository;

    private final MagazineRepository magazineRepository;

    private final LikesRepository likesRepository;

    private final ImageRepository imageRepository;

    public void initAddress() {
        List<Address> addressList = new ArrayList<>();

        addressList.add(new Address("07281", "서울특별시", "Seoul", "영등포구", "Yeongdeungpo-gu", "", "", "115604154433", "선유로13길", "Seonyu-ro 13-gil", "0", "5", "0", "1156012400100020002037439", "", "문래동 현대홈시티2", "1156012400", "문래동6가", "", "문래동", "0", "2", "01", "2", "", "", 1L, 1L));
        addressList.add(new Address("07282", "서울특별시", "Seoul", "영등포구", "Yeongdeungpo-gu", "", "", "115604154461", "선유로9길", "Seonyu-ro 9-gil", "0", "30", "0", "1156012400100210000000005", "", "문래롯데캐슬", "1156012400", "문래동6가", "", "문래동", "0", "57", "02", "0", "", "", 1L, 1L));

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

        imageList.add(createImage("/image/url/1", postRepository.findById(1L).orElse(null)));
        imageList.add(createImage("/image/url/2", postRepository.findById(3L).orElse(null)));
        imageList.add(createImage("/image/url/3", postRepository.findById(11L).orElse(null)));

        imageRepository.saveAllAndFlush(imageList);
        em.clear();
    }

    private static User createUser(String providerId, ProviderType providerType) {
        return new User(providerId, providerType);
    }

    private static Address createAddress(String sido, String sidoEn) {
        return new Address(sido, sidoEn, 1L, 1L);
    }

    private static Category createCategory(String name) {
        return new Category(name, 1L, 1L);
    }

    private static Tattoo createTattoo(Category category, TattooType tattooType, String name, String description, Long price) {
        return new Tattoo(name, description, price, category, tattooType, 1L, 1L);
    }

    private static Magazine createMagazine(String title) {
        return new Magazine(title, 1L, 1L);
    }

    private static Likes createLikes(Post post, User user, PostType postType) {
        return new Likes(post, user, postType);
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

    private static Image createImage(String imageUrl, Post post) {
        return new Image(imageUrl, post);
    }
}