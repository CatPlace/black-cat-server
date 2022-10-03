package com.spring.blackcat.domain;

import com.spring.blackcat.code.Role;
import com.spring.blackcat.domain.post.Post;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class User {

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

    private String regId;
    private LocalDateTime regDt;
    private String modId;
    private LocalDateTime modDt;

    @PrePersist
    void createdAt() {
        this.regDt = LocalDateTime.now();
    }

    @PreUpdate
    void updatedAt() {
        this.modDt = LocalDateTime.now();
    }
}
