package com.spring.blackcat.tattoo;

import com.spring.blackcat.category.Category;
import com.spring.blackcat.code.PostType;
import com.spring.blackcat.code.TattooType;
import com.spring.blackcat.post.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@DiscriminatorValue(PostType.Values.TATTOO)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public Tattoo(String name, Category category, TattooType tattooType, String registerId, String modifierId) {
        super(registerId, modifierId);
        this.name = name;
        this.category = category;
        this.tattooType = tattooType;
    }

    public void changeCategory(Category category) {
        this.category.getTattoos().remove(this);
        this.category = category;
        category.getTattoos().add(this);
    }
}
