package com.spring.blackcat.estimate;

import com.spring.blackcat.common.code.PostType;
import com.spring.blackcat.post.Post;
import com.spring.blackcat.user.User;
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

    public Estimate(User user) {
        super(user);
    }

    public void updateEstimate(String description) {
        this.description = description;
    }
}
