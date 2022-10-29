package com.spring.blackcat.user;

import com.spring.blackcat.common.BaseTimeEntity;
import com.spring.blackcat.common.code.ProviderType;
import com.spring.blackcat.likes.Likes;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
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

    // TODO: 추후 타투이스트 및 관리자 권한 구현 시 Profile 정보 필요

    public User(String providerId, ProviderType providerType) {
        this.providerId = providerId;
        this.providerType = providerType;
    }

    // TODO: 사용자 정의 ID 와 별개로 Auto Increment 식별자 ID 가지도록 변경 검토
//    @Id
//    @Column(name = "user_id")
//    private String id;
//
//    private String password;
//
//    @Column(name = "user_name")
//    private String name;
//
//    @Enumerated(EnumType.STRING)
//    private Role role;
//
//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "address_id")
//    private Address address;
//

//
//    private String registerId;
//    private String modifierId;


//    public void changeAddress(Address address) {
//        this.address.getUsers().remove(this);
//        this.address = address;
//        address.getUsers().add(this);
//    }

//    public void changePassword(String password) {
//        this.password = password;
//    }
}
