package com.spring.blackcat.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    @Column(name = "category_name")
    private String name;

    @OneToMany(mappedBy = "category", cascade = ALL)
    private List<Tattoo> tattoos = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_category_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    private String regId;
    private LocalDateTime regDt;
    private String modId;
    private LocalDateTime modDt;

    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this);
    }

    @PrePersist
    void createdAt() {
        this.regDt = LocalDateTime.now();
    }

    @PreUpdate
    void updatedAt() {
        this.modDt = LocalDateTime.now();
    }
}
