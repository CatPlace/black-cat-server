package com.spring.blackcat.categoryPost;

import com.spring.blackcat.category.Category;
import com.spring.blackcat.common.BaseTimeEntity;
import com.spring.blackcat.post.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryPost extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "category_post_id")
    private Long id;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public CategoryPost(Post post, Category category) {
        this.post = post;
        this.category = category;
    }
}
