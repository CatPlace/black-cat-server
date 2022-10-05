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

    @Column(name = "tattoo_name")
    private String name;

    @Column(name = "tattoo_price")
    private Long price;

    @Column(name = "tattoo_description")
    private String description;

    @Column(name = "tattoo_star")
    private int star;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Enumerated(EnumType.STRING)
    private TattooType tattooType;
}
