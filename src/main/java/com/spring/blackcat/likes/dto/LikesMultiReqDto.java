package com.spring.blackcat.likes.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LikesMultiReqDto {
    List<Long> postIds = new ArrayList<>();
}
