package com.spring.blackcat.likes;

import com.spring.blackcat.code.PostType;
import com.spring.blackcat.common.BaseTimeEntity;
import com.spring.blackcat.post.Post;
import com.spring.blackcat.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class Likes extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "likes_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Enumerated(EnumType.STRING)
    private PostType postType;

    private String registerId;
    private String modifierId;
}
