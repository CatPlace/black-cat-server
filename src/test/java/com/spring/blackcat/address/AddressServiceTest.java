package com.spring.blackcat.address;

import com.spring.blackcat.address.dto.AddressResDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class AddressServiceTest {

    @Autowired
    AddressService addressService;

    @Autowired
    AddressRepository addressRepository;

    @Test
    @DisplayName("특정 문자열을 포함하는 주소 검색")
    void findAddress() {
        // given
        Address address1 = new Address("07281", "서울특별시", "Seoul", "영등포구", "Yeongdeungpo-gu", "", "", "115604154433", "선유로13길", "Seonyu-ro 13-gil", "0", "5", "0", "1156012400100020002037439", "", "문래동 현대홈시티2", "1156012400", "문래동6가", "", "문래동", "0", "2", "01", "2", "", "", 1L, 1L);
        Address address2 = new Address("07282", "서울특별시", "Seoul", "영등포구", "Yeongdeungpo-gu", "", "", "115604154461", "선유로9길", "Seonyu-ro 9-gil", "0", "30", "0", "1156012400100210000000005", "", "문래롯데캐슬", "1156012400", "문래동6가", "", "문래동", "0", "57", "02", "0", "", "", 1L, 1L);
        addressRepository.save(address1);
        addressRepository.save(address2);

        PageRequest pageRequest = PageRequest.of(0, 20, Sort.DEFAULT_DIRECTION, "zipCode");

        // when
        Page<AddressResDto> findList = addressService.findAddress(pageRequest, "선유로 13길");

        // then
        assertThat(findList.getNumber()).isEqualTo(0);
        assertThat(findList.getSize()).isEqualTo(20);
        assertThat(findList.getNumberOfElements()).isEqualTo(1);
    }
}