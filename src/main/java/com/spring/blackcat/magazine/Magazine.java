package com.spring.blackcat.magazine;

import com.spring.blackcat.code.PostType;
import com.spring.blackcat.post.Post;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@DiscriminatorValue(PostType.Values.MAGAZINE)
@Getter
@Setter
public class Magazine extends Post {

    @Id
    @GeneratedValue
    @Column(name = "magazine_id")
    private Long id;

    @Column(name = "magazine_name")
    private String name;
}
