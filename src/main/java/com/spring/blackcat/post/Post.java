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
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@DiscriminatorColumn(name = "postTypeCd")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @OneToMany(mappedBy = "post", cascade = ALL, orphanRemoval = true)
    private List<Likes> likes = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(insertable = false, updatable = false)
    private PostType postTypeCd;

    private String registerId;
    private String modifierId;

    public Post(String registerId, String modifierId) {
        this.registerId = registerId;
        this.modifierId = modifierId;
    }
}
