package com.spring.blackcat.magazine;

import com.spring.blackcat.common.code.PostType;
import com.spring.blackcat.post.Post;
import com.spring.blackcat.user.User;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@DiscriminatorValue(PostType.Values.MAGAZINE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
public class Magazine extends Post {
    @Builder.Default
    @Setter
    private Boolean isMain = false;

    private String imageUrl;

    @Builder.Default
    @OneToMany(mappedBy = "magazine", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cell> cellList = new ArrayList<>();

    public Magazine(String title, User register) {
        super(title, register);
        this.isMain = false;
        this.cellList = new ArrayList<>();
    }

}
