package com.spring.blackcat.tattoo;

import com.spring.blackcat.common.code.TattooType;
import com.spring.blackcat.common.response.ResponseDto;
import com.spring.blackcat.common.response.ResponseUtil;
import com.spring.blackcat.common.security.interceptor.UserId;
import com.spring.blackcat.tattoo.dto.CreateTattooDto;
import com.spring.blackcat.tattoo.dto.DeleteTattoosReqDto;
import com.spring.blackcat.tattoo.dto.UpdateTattooReqDto;
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
    public ResponseDto getAllTattoos(@PageableDefault(page = 0, size = 20, sort = "likesCount", direction = Sort.Direction.DESC) Pageable pageable,
                                     @RequestParam(name = "tattooTypes", required = false) List<TattooType> tattooTypes,
                                     @RequestParam(name = "addressIds", required = false) List<Long> addressIds) {
        return ResponseUtil.SUCCESS("모든 타투 조회 성공", this.tattooService.getAllTattoos(pageable, tattooTypes, addressIds));
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseDto getTattoosByCategoryId(@PageableDefault(page = 0, size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                              @PathVariable("categoryId") Long categoryId,
                                              @RequestParam(name = "tattooTypes", required = false) List<TattooType> tattooTypes,
                                              @RequestParam(name = "addressIds", required = false) List<Long> addressIds) {
        return ResponseUtil.SUCCESS("카테고리별 타투 조회 성공", this.tattooService.getTattoosByCategoryId(pageable, categoryId, tattooTypes, addressIds));
    }

    @GetMapping("/{tattooId}")
    public ResponseDto getTattooById(@PathVariable("tattooId") Long tattooId,
                                     @UserId Long userId) {
        return ResponseUtil.SUCCESS("타투 상세 조회 성공", this.tattooService.getTattooById(tattooId, userId));
    }

    @GetMapping("/users/{tattooistId}")
    public ResponseDto getTattooByUserId(@PageableDefault(page = 0, size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                         @PathVariable Long tattooistId) {
        return ResponseUtil.SUCCESS("타투이스트 작품 조회 성공", this.tattooService.getTattoosByUserId(pageable, tattooistId));
    }


    @PostMapping()
    public ResponseDto createTattoo(@RequestPart("tattooInfo") @Valid CreateTattooDto createTattooDto,
                                    @RequestPart("images") List<MultipartFile> images,
                                    @UserId Long userId) {
        return ResponseUtil.SUCCESS("타투 생성 성공", this.tattooService.createTattoo(userId, createTattooDto, images));
    }

    @PatchMapping("/{tattooId}")
    public ResponseDto updateTattoo(@PathVariable("tattooId") Long tattooId,
                                    @RequestPart("tattooInfo") @Valid UpdateTattooReqDto updateTattooReqDto,
                                    @RequestPart("images") List<MultipartFile> images,
                                    @UserId Long userId) {
        return ResponseUtil.SUCCESS("타투 수정 성공", this.tattooService.updateTattoo(userId, tattooId, updateTattooReqDto, images));
    }

    @DeleteMapping()
    public ResponseDto deleteTattoos(@RequestBody DeleteTattoosReqDto deleteTattoosReqDto) {
        return ResponseUtil.SUCCESS("타투 삭제 성공", this.tattooService.deleteTattoos(deleteTattoosReqDto));
    }
}
