package com.spring.blackcat.magazine;

import com.spring.blackcat.code.PostType;
import com.spring.blackcat.post.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@DiscriminatorValue(PostType.Values.MAGAZINE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Magazine extends Post {

    @Column(name = "magazine_name")
    private String name;

    public Magazine(String name, String registerId, String modifierId) {
        super(registerId, modifierId);
        this.name = name;
    }
}
