package com.spring.blackcat.profile;

import com.spring.blackcat.common.code.PostType;
import com.spring.blackcat.post.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@DiscriminatorValue(PostType.Values.PROFILE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile extends Post {
    private String introduce;
}
