package com.spring.blackcat.tattoo;

import com.spring.blackcat.common.response.ResponseDto;
import com.spring.blackcat.common.response.ResponseUtil;
import com.spring.blackcat.common.security.interceptor.UserId;
import com.spring.blackcat.tattoo.dto.CreateTattooDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/v1/tattoos")
@RequiredArgsConstructor
@RestController
public class TattooController {
    private final TattooService tattooService;

    @GetMapping()
    public ResponseDto getAllTattoos(@PageableDefault(page = 0, size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                     @UserId Long userId) {
        return ResponseUtil.SUCCESS("모든 타투 조회 성공", this.tattooService.getAllTattoos(pageable, userId));
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseDto getTattoosByCategoryId(@PageableDefault(page = 0, size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                              @UserId Long userId,
                                              @PathVariable("categoryId") Long categoryId) {
        return ResponseUtil.SUCCESS("카테고리별 타투 조회 성공", this.tattooService.getTattoosByCategoryId(pageable, userId, categoryId));
    }

    @GetMapping("/{tattooId}")
    public ResponseDto getTattooById(@PathVariable("tattooId") Long tattooId,
                                     @UserId Long userId) {
        return ResponseUtil.SUCCESS("타투 상세 조회 성공", this.tattooService.getTattooById(tattooId, userId));
    }

    @PostMapping()
    public ResponseDto createTattoo(@RequestPart("tattooInfo") @Valid CreateTattooDto createTattooDto,
                                    @RequestPart("images") List<MultipartFile> images,
                                    @UserId Long userId) {
        return ResponseUtil.SUCCESS("타투 생성 성공", this.tattooService.createTattoo(userId, createTattooDto, images));
    }

}
