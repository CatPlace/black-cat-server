package com.spring.blackcat.address;

import com.spring.blackcat.address.dto.AddressResDto;

import java.util.List;

public interface AddressService {

    List<AddressResDto> findAll();

//    Page<AddressResDto> findAddress(Pageable pageable, String findString);
}
