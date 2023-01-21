package com.spring.blackcat.profile;

import com.spring.blackcat.common.response.ResponseDto;
import com.spring.blackcat.common.response.ResponseUtil;
import com.spring.blackcat.common.security.interceptor.UserId;
import com.spring.blackcat.profile.dto.UpsertProfileReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/api/v1")
@RequiredArgsConstructor
@RestController
public class ProfileController {
    private final ProfileService profileService;

    @PostMapping("/tattooists/profile")
    public ResponseDto upsertTattooistProfile(@RequestPart("profileInfo") UpsertProfileReqDto upsertProfileReqDto,
                                              @RequestPart("images") List<MultipartFile> images,
                                              @UserId Long userId) {
        return ResponseUtil.SUCCESS("타투이스트 프로필 등록 성공", this.profileService.upsertTattooistProfile(userId, upsertProfileReqDto, images));
    }
}
