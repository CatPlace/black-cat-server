package com.spring.blackcat.profile;

import com.spring.blackcat.common.response.ResponseDto;
import com.spring.blackcat.common.response.ResponseUtil;
import com.spring.blackcat.common.security.interceptor.UserId;
import com.spring.blackcat.profile.dto.UpsertProfileReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/api/v1")
@RequiredArgsConstructor
@RestController
public class ProfileController {
    private final ProfileService profileService;

    @PostMapping("/tattooists/profile")
    public ResponseDto upsertTattooistProfile(@RequestPart(value = "profileInfo") UpsertProfileReqDto upsertProfileReqDto,
                                              @RequestPart(value = "images", required = false) List<MultipartFile> images,
                                              @UserId Long userId) {
        return ResponseUtil.SUCCESS("타투이스트 프로필 등록 성공", this.profileService.upsertTattooistProfile(userId, upsertProfileReqDto, images));
    }

    @GetMapping("/tattooists/profile")
    public ResponseDto getTattooistProfile(@UserId Long userId) {
        return ResponseUtil.SUCCESS("타투이스트 프로필 조회 성공", this.profileService.getTattooistProfile(userId));
    }
}
