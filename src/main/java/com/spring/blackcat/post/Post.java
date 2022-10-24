package com.spring.blackcat.post;

import com.spring.blackcat.common.BaseTimeEntity;
import com.spring.blackcat.common.code.PostType;
import com.spring.blackcat.image.Image;
import com.spring.blackcat.likes.Likes;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@DiscriminatorColumn(name = "postType")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public abstract class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "post_id")
    private Long id;

    private String title;

    @OneToMany(mappedBy = "post", cascade = ALL, orphanRemoval = true)
    private List<Likes> likes = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(insertable = false, updatable = false)
    private PostType postType;
    private String registerId;
    private String modifierId;

    public Post(String title, String registerId, String modifierId) {
        this.title = title;
        this.registerId = registerId;
        this.modifierId = modifierId;
    }
}
