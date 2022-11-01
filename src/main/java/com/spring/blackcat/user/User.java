package com.spring.blackcat.user;

import com.spring.blackcat.address.Address;
import com.spring.blackcat.common.BaseTimeEntity;
import com.spring.blackcat.common.code.ProviderType;
import com.spring.blackcat.common.code.Role;
import com.spring.blackcat.likes.Likes;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String providerId;

    @Enumerated(EnumType.STRING)
    private ProviderType providerType;

    @OneToMany(mappedBy = "user", cascade = ALL, orphanRemoval = true)
    private List<Likes> likes = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

//    private String password;

    @Column(name = "user_name")
    private String name;

    @Column(unique = true)
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String registerId;
    private String modifierId;

    public User(String providerId, ProviderType providerType, String nickname, Role role, String registerId, String modifierId) {
        this.providerId = providerId;
        this.providerType = providerType;
        this.nickname = nickname;
        this.role = role;
        this.registerId = registerId;
        this.modifierId = modifierId;
    }

//    public void changePassword(String password) {
//        this.password = password;
//    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void changeRole(Role role) {
        this.role = role;
    }

    public void changeAddress(Address address) {
        this.address.getUsers().remove(this);
        this.address = address;
        address.getUsers().add(this);
    }
}
