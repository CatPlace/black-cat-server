package com.spring.blackcat.category;

import com.spring.blackcat.common.BaseTimeEntity;
import com.spring.blackcat.tattoo.Tattoo;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseTimeEntity {

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
    private List<Category> children = new ArrayList<>();

    private String registerId;
    private String modifierId;

    @Builder
    public Category(String name, String registerId, String modifierId) {
        this.name = name;
        this.registerId = registerId;
        this.modifierId = modifierId;
    }

    public void addChildCategory(Category child) {
        this.children.add(child);
        if (!child.getParent().equals(this)) {
            child.changeParent(this);
        }
    }

    public void changeParent(Category parent) {
        if (this.parent != null) {
            this.parent.getChildren().remove(this);
        }
        this.parent = parent;
        if (!parent.getChildren().contains(this)) {
            parent.addChildCategory(this);
        }
    }
}
