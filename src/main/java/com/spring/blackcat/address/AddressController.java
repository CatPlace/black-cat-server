package com.spring.blackcat.address;

import com.spring.blackcat.address.dto.AddressResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/addresses")
public class AddressController {

    private final AddressService addressService;

    @GetMapping("/search")
    public Page<AddressResDto> findAddress(
            @PageableDefault(size = 20) @SortDefault(sort = "id") Pageable pageable,
            @RequestParam String query) {
        return addressService.findAddress(pageable, query);
    }
}
