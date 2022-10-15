package com.spring.blackcat.address;

import com.spring.blackcat.address.dto.AddressResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    /**
     * 특정 문자열을 포함하는 주소 검색
     */
    @Override
    public Page<AddressResDto> findAddress(Pageable pageable, String query) {
        String findString = query.replaceAll("\\s", "");
        Page<Address> addresses = addressRepository.findBySearchStringContains(pageable, findString);
        return addresses.map(AddressResDto::new);
    }
}
