package com.spring.blackcat.user;

import com.spring.blackcat.address.Address;
import com.spring.blackcat.common.BaseTimeEntity;
import com.spring.blackcat.common.code.Gender;
import com.spring.blackcat.common.code.ProviderType;
import com.spring.blackcat.common.code.Role;
import com.spring.blackcat.likes.Likes;
import com.spring.blackcat.post.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Date;
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

    private String email;

    private String phoneNumber;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String openChatLink;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = ALL)
    private List<Post> posts;

    private Long registerId;
    private Long modifierId;

    public User(String providerId, ProviderType providerType, String nickname, Role role, Long registerId, Long modifierId) {
        this.providerId = providerId;
        this.providerType = providerType;
        this.nickname = nickname;
        this.role = role;
        this.registerId = registerId;
        this.modifierId = modifierId;
    }

    public User(String providerId, ProviderType providerType, Address address, String name, String nickname,
                String email, String phoneNumber, Gender gender, String openChatLink, Role role,
                Long registerId, Long modifierId) {
        this.providerId = providerId;
        this.providerType = providerType;
        this.address = address;
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.openChatLink = openChatLink;
        this.role = role;
        this.registerId = registerId;
        this.modifierId = modifierId;
    }

    //    public void changePassword(String password) {
//        this.password = password;
//    }
    public void updateAdditionalInfo(String name, String email, String phoneNumber, Gender gender, Address address) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.address = address;
    }

    public void updateTattooistInfo(Address address, String openChatLink, Role role) {
        this.address = address;
        this.openChatLink = openChatLink;
        this.role = role;
    }

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
