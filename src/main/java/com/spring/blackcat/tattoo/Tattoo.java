package com.spring.blackcat.tattoo;

import com.spring.blackcat.common.code.PostType;
import com.spring.blackcat.common.code.TattooType;
import com.spring.blackcat.post.Post;
import com.spring.blackcat.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@DiscriminatorValue(PostType.Values.TATTOO)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tattoo extends Post {
    @Column(name = "tattoo_price")
    private Long price;

    @Column(name = "tattoo_description")
    private String description;

    @Enumerated(EnumType.STRING)
    private TattooType tattooType;

    public Tattoo(String title, String description, Long price,
                  TattooType tattooType, User user) {
        super(title, user);
        this.description = description;
        this.price = price;
        this.tattooType = tattooType;
    }

    public Tattoo(Long id, String title, String description, Long price,
                  TattooType tattooType, User user) {
        super(id, title, user);
        this.description = description;
        this.price = price;
        this.tattooType = tattooType;
    }

    public void updateTattoo(String title, String description, Long price, TattooType tattooType) {
        this.setTitle(title);
        this.description = description;
        this.price = price;
        this.tattooType = tattooType;
    }
}
