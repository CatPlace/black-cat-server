package com.spring.blackcat.likes;

import com.spring.blackcat.code.PostType;
import com.spring.blackcat.common.BaseTimeEntity;
import com.spring.blackcat.post.Post;
import com.spring.blackcat.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Likes extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "likes_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private PostType postType;

    private String registerId;
    private String modifierId;

    public Likes(Post post, User user, PostType postType, String registerId, String modifierId) {
        this.post = post;
        this.user = user;
        this.postType = postType;
        this.registerId = registerId;
        this.modifierId = modifierId;
        post.getLikes().add(this);
        user.getLikes().add(this);
    }
}
