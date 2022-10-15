package com.spring.blackcat.likes.dto;

import com.spring.blackcat.user.User;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikesUserResDto {
    private String id;
    private String name;

    public LikesUserResDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
    }
}
