package com.spring.blackcat.likes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class LikesControllerTest {

    @Autowired
    LikesController likesController;

    @Autowired
    MockMvc mock;

    @Test
    @DisplayName("특정 게시물 좋아요 조회")
    void isLikedThisPostTest() throws Exception {
        mock.perform(get("/api/v1/likes/posts/1")).andExpect(status().isOk());
    }

    @Test
    @DisplayName("특정 게시물 좋아요 활성화")
    void likesOnTest() throws Exception {
        mock.perform(post("/api/v1/likes/posts/1")).andExpect(status().isOk());
    }

    @Test
    @DisplayName("특정 게시물 좋아요 비활성화")
    void likesOffTest() throws Exception {
        mock.perform(delete("/api/v1/likes/posts/1")).andExpect(status().isOk());
    }

    @Test
    @DisplayName("특정 게시물을 좋아요한 유저 리스트 조회")
    void likesUsersTest() throws Exception {
        mock.perform(get("/api/v1/likes/posts/1/users")).andExpect(status().isOk());
    }

    @Test
    @DisplayName("특정 유저의 좋아요한 게시물 리스트 조회")
    void likesPostsTest() throws Exception {
        mock.perform(get("/api/v1/likes/posts")).andExpect(status().isOk());
    }
}