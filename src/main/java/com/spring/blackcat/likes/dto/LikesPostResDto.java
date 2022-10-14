package com.spring.blackcat.likes.dto;

import com.spring.blackcat.code.PostType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LikesPostResDto {
    private Long id;
    private PostType postTypeCd;
}
