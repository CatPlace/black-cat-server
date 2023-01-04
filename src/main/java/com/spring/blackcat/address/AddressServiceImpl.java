package com.spring.blackcat.address;

import com.spring.blackcat.address.dto.AddressResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    /**
     * 전체 지역 검색
     */
    @Override
    public List<AddressResDto> findAll() {
        List<Address> addresses = addressRepository.findAll();
        return addresses.stream()
                .map(AddressResDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 특정 문자열을 포함하는 주소 검색
     */
//    @Override
//    public Page<AddressResDto> findAddress(Pageable pageable, String query) {
//        String findString = query.replaceAll("\\s", "");
//        Page<Address> addresses = addressRepository.findBySearchStringContains(pageable, findString);
//        return addresses.map(AddressResDto::new);
//    }
}
