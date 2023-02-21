package com.spring.blackcat.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GetProfileResDto {
    Long profileId;
    String introduce;
    List<String> imageUrls;
    String userName;
    Long addressId;
    List<String> userImgUrls;
}
