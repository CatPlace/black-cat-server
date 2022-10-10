package com.spring.blackcat.magazine;

import com.spring.blackcat.code.PostType;
import com.spring.blackcat.post.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@DiscriminatorValue(PostType.Values.MAGAZINE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Magazine extends Post {

    private String title;

    @OneToMany(mappedBy = "magazine", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cell> cellList = new ArrayList<>();

    public Magazine(String title, String registerId, String modifierId) {
        super(registerId, modifierId);
        this.name = title;
    
}
