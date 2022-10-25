package com.spring.blackcat.tattoo;

import com.spring.blackcat.tattoo.dto.CreateTattooDto;
import com.spring.blackcat.tattoo.dto.CreateTattooResDto;
import com.spring.blackcat.tattoo.dto.GetTattooResDto;
import com.spring.blackcat.tattoo.dto.GetTattoosResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

//path variable userId 수정 필요, request url userId 수정  필요)
@RequiredArgsConstructor
@RestController
public class TattooController {
    private final TattooService tattooService;

    @GetMapping("/tattoos/{userId}")
    public Page<GetTattoosResDto> getAllTattoos(@PageableDefault(page = 0, size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                                @PathVariable("userId") String userId) {
        return this.tattooService.getAllTattoos(pageable, userId);
    }

    @GetMapping("/tattoos/{userId}/categories/{categoryId}")
    public Page<GetTattoosResDto> getTattoosByCategoryId(@PageableDefault(page = 0, size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                                         @PathVariable("userId") String userId, @PathVariable("categoryId") Long categoryId) {
        return this.tattooService.getTattoosByCategoryId(pageable, userId, categoryId);
    }

    @GetMapping("/tattoos/{tattooId}/{userId}")
    public GetTattooResDto getTattooById(@PathVariable("tattooId") Long tattooId,
                                         @PathVariable("userId") String userId) {
        return this.tattooService.getTattooById(tattooId, userId);
    }

    @PostMapping("/tattoos/{userId}")
    public CreateTattooResDto createTattoo(@RequestPart("tattooInfo") CreateTattooDto createTattooDto,
                                           @RequestPart("images") List<MultipartFile> images,
                                           @PathVariable("userId") String userId) {
        return this.tattooService.createTattoo(userId, createTattooDto, images);
    }

}
