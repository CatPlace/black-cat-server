package com.spring.blackcat.likes;

import com.spring.blackcat.address.Address;
import com.spring.blackcat.address.AddressRepository;
import com.spring.blackcat.category.Category;
import com.spring.blackcat.category.CategoryRepository;
import com.spring.blackcat.common.code.PostType;
import com.spring.blackcat.common.code.Role;
import com.spring.blackcat.common.code.TattooType;
import com.spring.blackcat.likes.dto.LikesPostResDto;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class LikesRepositoryTest {

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
    @DisplayName("Post ID와 User ID로 좋아요 조회")
    public void findByPostIdAndUserId() {
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

        Likes likes = new Likes(post, user, post.getPostType());
        likesRepository.save(likes);

        // when
        Likes findLikes = likesRepository.findByPostIdAndUserId(post.getId(), user.getId()).orElseGet(Likes::new);

        // then
        assertThat(findLikes).isEqualTo(likes);
    }

    @Test
    @DisplayName("Post ID로 좋아요 리스트 조회")
    public void findByPostId() {
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

        Likes likes1 = new Likes(post, user1, post.getPostType());
        likesRepository.save(likes1);

        Likes likes2 = new Likes(post, user2, post.getPostType());
        likesRepository.save(likes2);

        PageRequest pageRequest = PageRequest.of(0, 50, Sort.Direction.DESC, "id");

        // when
        Page<LikesUserResDto> findList = likesRepository.findLikesUsersByPostId(pageRequest, post.getId());

        // then
        assertThat(findList.getNumber()).isEqualTo(0);
        assertThat(findList.getSize()).isEqualTo(50);
        assertThat(findList.getNumberOfElements()).isEqualTo(2);
    }

    @Test
    @DisplayName("User ID로 좋아요 리스트 조회")
    public void findByUserId() {
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

        Likes likes1 = new Likes(post1, user, post2.getPostType());
        likesRepository.save(likes1);

        Likes likes2 = new Likes(post2, user, post2.getPostType());
        likesRepository.save(likes2);

        PageRequest pageRequest = PageRequest.of(0, 50, Sort.Direction.DESC, "id");

        // when
        Page<LikesPostResDto> findListWithPostType = likesRepository.findLikesPostsByUserIdAndPostType(pageRequest, user.getId(), PostType.TATTOO);
        Page<LikesPostResDto> findPostTypeNullList = likesRepository.findLikesPostsByUserIdAndPostType(pageRequest, user.getId(), null);

        // then
        assertThat(findListWithPostType.getNumber()).isEqualTo(0);
        assertThat(findListWithPostType.getSize()).isEqualTo(50);
        assertThat(findListWithPostType.getNumberOfElements()).isEqualTo(2);
        assertThat(findPostTypeNullList.getNumber()).isEqualTo(0);
        assertThat(findPostTypeNullList.getSize()).isEqualTo(50);
        assertThat(findPostTypeNullList.getNumberOfElements()).isEqualTo(2);
    }

    @Test
    @DisplayName("좋아요 삭제")
    public void delete() {
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

        Likes likes = new Likes(post, user, post.getPostType());
        likesRepository.save(likes);

        // when
        likesRepository.delete(likes);
        boolean present = likesRepository.findById(likes.getId()).isPresent();

        // then
        assertThat(present).isFalse();
    }
}
