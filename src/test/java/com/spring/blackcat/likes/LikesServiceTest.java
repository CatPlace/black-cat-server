package com.spring.blackcat.likes;

import com.spring.blackcat.address.Address;
import com.spring.blackcat.address.AddressRepository;
import com.spring.blackcat.category.Category;
import com.spring.blackcat.category.CategoryRepository;
import com.spring.blackcat.code.Role;
import com.spring.blackcat.code.TattooType;
import com.spring.blackcat.likes.dto.LikesPostResDto;
import com.spring.blackcat.likes.dto.LikesStatusResDto;
import com.spring.blackcat.likes.dto.LikesUserResDto;
import com.spring.blackcat.post.Post;
import com.spring.blackcat.post.PostRepository;
import com.spring.blackcat.tattoo.Tattoo;
import com.spring.blackcat.tattoo.TattooRepository;
import com.spring.blackcat.user.User;
import com.spring.blackcat.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class LikesServiceTest {

    @Autowired
    LikesService likesService;

    @Autowired
    LikesRepository likesRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    TattooRepository tattooRepository;

    @Autowired
    UserRepository userRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("특정 게시물 좋아요 조회")
    void isLikedThisPost() {
        // given
        Category category = new Category("카테고리", "Test", "Test");
        categoryRepository.save(category);

        Tattoo tattoo = new Tattoo("타투", "테스트타투", 0L, 0, category, TattooType.DESIGN, "Test", "Test");
        tattooRepository.save(tattoo);
        Long tattooId = tattoo.getId();

        Address address = new Address("서울", "Seoul", "Test", "Test");
        addressRepository.save(address);

        User user = new User("Test", "테스트", Role.BASIC, address, "Test", "Test");
        userRepository.save(user);

        em.flush();

        Post post = postRepository.findById(tattooId).orElseThrow();

        Likes likes = new Likes(post, user, post.getPostTypeCd());
        likesRepository.save(likes);

        // when
        LikesStatusResDto likesStatusResDto = likesService.isLikedThisPost(post.getId(), user.getId());

        // then
        assertThat(likesStatusResDto.isLiked()).isTrue();
    }

    @Test
    @DisplayName("특정 게시물 좋아요 활성화")
    void likesOff() {
        // given
        Category category = new Category("카테고리", "Test", "Test");
        categoryRepository.save(category);

        Tattoo tattoo = new Tattoo("타투", "테스트타투", 0L, 0, category, TattooType.DESIGN, "Test", "Test");
        tattooRepository.save(tattoo);
        Long tattooId = tattoo.getId();

        Address address = new Address("서울", "Seoul", "Test", "Test");
        addressRepository.save(address);

        User user = new User("Test", "테스트", Role.BASIC, address, "Test", "Test");
        userRepository.save(user);

        em.flush();

        Post post = postRepository.findById(tattooId).orElseThrow();

        // when
        LikesStatusResDto likesStatusResDto = likesService.likesOn(post.getId(), user.getId());

        // then
        assertThat(likesStatusResDto.isLiked()).isTrue();
    }

    @Test
    @DisplayName("특정 게시물 좋아요 비활성화")
    void likesOn() {
        // given
        Category category = new Category("카테고리", "Test", "Test");
        categoryRepository.save(category);

        Tattoo tattoo = new Tattoo("타투", "테스트타투", 0L, 0, category, TattooType.DESIGN, "Test", "Test");
        tattooRepository.save(tattoo);
        Long tattooId = tattoo.getId();

        Address address = new Address("서울", "Seoul", "Test", "Test");
        addressRepository.save(address);

        User user = new User("Test", "테스트", Role.BASIC, address, "Test", "Test");
        userRepository.save(user);

        em.flush();

        Post post = postRepository.findById(tattooId).orElseThrow();

        Likes likes = new Likes(post, user, post.getPostTypeCd());
        likesRepository.save(likes);

        // when
        LikesStatusResDto likesStatusResDto = likesService.likesOff(post.getId(), user.getId());

        // then
        assertThat(likesStatusResDto.isLiked()).isFalse();
    }

    @Test
    @DisplayName("특정 게시물을 좋아요한 유저 리스트 조회")
    void findUsersByPostId() {
        // given
        Category category = new Category("카테고리", "Test", "Test");
        categoryRepository.save(category);

        Tattoo tattoo = new Tattoo("타투", "테스트타투", 0L, 0, category, TattooType.DESIGN, "Test", "Test");
        tattooRepository.save(tattoo);
        Long tattooId = tattoo.getId();

        Address address = new Address("서울", "Seoul", "Test", "Test");
        addressRepository.save(address);

        User user1 = new User("Test", "테스트", Role.BASIC, address, "Test", "Test");
        userRepository.save(user1);

        User user2 = new User("Test", "테스트", Role.BASIC, address, "Test", "Test");
        userRepository.save(user2);

        em.flush();

        Post post = postRepository.findById(tattooId).orElseThrow();

        Likes likes1 = new Likes(post, user1, post.getPostTypeCd());
        likesRepository.save(likes1);

        Likes likes2 = new Likes(post, user2, post.getPostTypeCd());
        likesRepository.save(likes2);

        // when
        List<LikesUserResDto> findList = likesService.findUsersByPostId(post.getId());

        // then
        assertThat(findList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("특정 유저의 좋아요한 게시물 리스트 조회")
    void findPostsByUserId() {
        // given
        Category category = new Category("카테고리", "Test", "Test");
        categoryRepository.save(category);

        Tattoo tattoo1 = new Tattoo("타투", "테스트타투", 0L, 0, category, TattooType.DESIGN, "Test", "Test");
        tattooRepository.save(tattoo1);
        Long tattooId1 = tattoo1.getId();

        Tattoo tattoo2 = new Tattoo("타투", "테스트타투", 0L, 0, category, TattooType.DESIGN, "Test", "Test");
        tattooRepository.save(tattoo2);
        Long tattooId2 = tattoo2.getId();

        Address address = new Address("서울", "Seoul", "Test", "Test");
        addressRepository.save(address);

        User user = new User("Test", "테스트", Role.BASIC, address, "Test", "Test");
        userRepository.save(user);

        em.flush();

        Post post1 = postRepository.findById(tattooId1).orElseThrow();
        Post post2 = postRepository.findById(tattooId2).orElseThrow();

        Likes likes1 = new Likes(post1, user, post2.getPostTypeCd());
        likesRepository.save(likes1);

        Likes likes2 = new Likes(post2, user, post2.getPostTypeCd());
        likesRepository.save(likes2);

        // when
        List<LikesPostResDto> findList = likesService.findPostsByUserId(user.getId());

        // then
        assertThat(findList.size()).isEqualTo(2);
    }
}