package com.spring.blackcat.magazine;

import com.spring.blackcat.code.PostType;
import com.spring.blackcat.post.Post;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue(PostType.Values.MAGAZINE)
@Getter
@Setter
public class Magazine extends Post {

    @Id
    @GeneratedValue
    @Column(name = "magazine_id")
    private Long id;

    @Column(name = "magazine_name")
    private String name;

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
