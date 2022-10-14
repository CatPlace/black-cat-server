package com.spring.blackcat.address;

import com.spring.blackcat.address.dto.AddressResDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AddressService {

    Page<AddressResDto> findAddress(Pageable pageable, String findString);
}
