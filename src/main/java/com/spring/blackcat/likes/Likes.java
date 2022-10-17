package com.spring.blackcat.likes;

import com.spring.blackcat.common.code.PostType;
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

    public Likes(Post post, User user, PostType postType) {
        this.post = post;
        this.user = user;
        this.postType = postType;
        post.getLikes().add(this);
        user.getLikes().add(this);
    }
}
