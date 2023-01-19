package com.spring.blackcat.estimate;

import com.spring.blackcat.common.code.PostType;
import com.spring.blackcat.post.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@DiscriminatorValue(PostType.Values.ESTIMATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Estimate extends Post {
    private String description;
}
