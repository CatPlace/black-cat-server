package com.spring.blackcat.user;

import com.spring.blackcat.address.Address;
import com.spring.blackcat.code.Role;
import com.spring.blackcat.common.BaseTimeEntity;
import com.spring.blackcat.post.Post;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class User extends BaseTimeEntity {

    @Id
    @Column(name = "user_id")
    private String id;

    @Column(name = "user_name")
    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToMany
    @JoinTable(name = "likes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id"))
    private List<Post> likePosts = new ArrayList<>();

    private String registrantId;
    private String modifierId;
}
