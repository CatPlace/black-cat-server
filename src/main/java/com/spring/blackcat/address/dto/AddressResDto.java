package com.spring.blackcat.address.dto;

import com.spring.blackcat.address.Address;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddressResDto {

    private Long id;
    //    private String zipCode;
    private String sido;

    public AddressResDto(Address address) {
        this.id = address.getId();
        this.sido = address.getSido();
//        this.zipCode = address.getZipCode();
//        this.address = (address.getSido() + " " + address.getSigungu() + " " + address.getUpmyen() + " " + address.getLoadNm() + " " + address.getBuildingNoBon() + " " + address.getSigunguBuildingNm()).replaceAll("\\s+", " ");
    }
}
