package com.spring.blackcat.user;

import com.spring.blackcat.address.Address;
import com.spring.blackcat.code.Role;
import com.spring.blackcat.common.BaseTimeEntity;
import com.spring.blackcat.likes.Likes;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
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
    
    @OneToMany(mappedBy = "user", cascade = ALL, orphanRemoval = true)
    private List<Likes> likes = new ArrayList<>();

    private String registerId;
    private String modifierId;
}
