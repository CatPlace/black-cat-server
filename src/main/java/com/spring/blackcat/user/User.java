package com.spring.blackcat.user;

import com.spring.blackcat.address.Address;
import com.spring.blackcat.code.Role;
import com.spring.blackcat.common.BaseTimeEntity;
import com.spring.blackcat.likes.Likes;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public User(String id, String name, Role role, Address address, String registerId, String modifierId) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.address = address;
        this.registerId = registerId;
        this.modifierId = modifierId;
    }
}
