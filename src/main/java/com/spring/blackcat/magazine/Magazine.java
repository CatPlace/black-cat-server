package com.spring.blackcat.magazine;

import com.spring.blackcat.code.PostType;
import com.spring.blackcat.post.Post;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(PostType.Values.MAGAZINE)
@Getter
@Setter
public class Magazine extends Post {

    @Column(name = "magazine_name")
    private String name;
}
