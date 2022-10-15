package com.spring.blackcat.post;

import com.spring.blackcat.code.PostType;
import com.spring.blackcat.common.BaseTimeEntity;
import com.spring.blackcat.likes.Likes;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Entity
@Getter
@DiscriminatorColumn(name = "postType")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @OneToMany(mappedBy = "post", cascade = ALL, orphanRemoval = true)
    private List<Likes> likes = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(insertable = false, updatable = false)
    private PostType postType;

    private String registerId;
    private String modifierId;

    public Post(String registerId, String modifierId) {
        this.registerId = registerId;
        this.modifierId = modifierId;
    }
}
