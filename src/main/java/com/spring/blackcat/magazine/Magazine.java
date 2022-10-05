package com.spring.blackcat.magazine;

import com.spring.blackcat.code.PostType;
import com.spring.blackcat.post.Post;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue(PostType.Values.MAGAZINE)
@Getter
@Setter
public class Magazine extends Post {

    private String title;

    @OneToMany(mappedBy = "magazine", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cell> cellList;
}
