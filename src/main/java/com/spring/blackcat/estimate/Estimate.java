package com.spring.blackcat.estimate;

import com.spring.blackcat.common.code.PostType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@DiscriminatorValue(PostType.Values.PROFILE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Estimate {
    private String description;
}
