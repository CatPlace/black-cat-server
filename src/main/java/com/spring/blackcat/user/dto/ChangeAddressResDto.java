package com.spring.blackcat.user.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeAddressResDto {
    private Long addressId;

    public ChangeAddressResDto(Long addressId) {
        this.addressId = addressId;
    }
}
