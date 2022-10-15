package com.spring.blackcat.likes;

import com.spring.blackcat.address.Address;
import com.spring.blackcat.address.AddressRepository;
import com.spring.blackcat.category.Category;
import com.spring.blackcat.category.CategoryRepository;
import com.spring.blackcat.code.Role;
import com.spring.blackcat.code.TattooType;
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

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class LikesTest {

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
    @DisplayName("생성 시점 양방향 연관관계 매핑")
    void associationMapping() {
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
        Likes likes = new Likes(post, user, post.getPostType());

        // then
        assertThat(post.getLikes().contains(likes)).isTrue();
        assertThat(user.getLikes().contains(likes)).isTrue();
    }
}