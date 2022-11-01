package com.spring.blackcat.user.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeAddressReqDto {
    private Long addressId;

    public ChangeAddressReqDto(Long addressId) {
        this.addressId = addressId;
    }
}
