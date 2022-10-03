package com.spring.blackcat.domain.post;

import com.spring.blackcat.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "post_division_cd")
@Getter
@Setter
public abstract class Post {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @ManyToMany(mappedBy = "likePosts")
    private List<User> likeUsers = new ArrayList<>();
}
