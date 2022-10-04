package com.spring.blackcat.tattoo;

import com.spring.blackcat.category.Category;
import com.spring.blackcat.code.PostType;
import com.spring.blackcat.code.TattooType;
import com.spring.blackcat.post.Post;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@DiscriminatorValue(PostType.Values.TATTOO)
@Getter
@Setter
public class Tattoo extends Post {

    @Id
    @GeneratedValue
    @Column(name = "tattoo_id")
    private Long id;

    @Column(name = "tattoo_name")
    private String name;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Enumerated(EnumType.STRING)
    private TattooType tattooType;
}