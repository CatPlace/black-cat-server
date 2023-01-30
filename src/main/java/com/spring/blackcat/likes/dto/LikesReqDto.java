package com.spring.blackcat.likes.dto;

import lombok.Data;

import java.util.List;

@Data
public class LikesReqDto {
    List<Long> postIds;
}
