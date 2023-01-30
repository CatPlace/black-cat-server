package com.spring.blackcat.likes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class LikesResDto {
    List<Long> postIds;
}
