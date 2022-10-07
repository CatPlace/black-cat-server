package com.spring.blackcat.tattoo;

import com.spring.blackcat.tattoo.dto.GetAllTattoosResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class TattooController {
    private final TattooService tattooService;

    @GetMapping("/tattoos/{userId}")
    public List<GetAllTattoosResDto> getAllTattoos(@PageableDefault(page = 0, size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, @PathVariable("userId") String userId) {
        return this.tattooService.getAllTattoos(pageable, userId);
    }
}
