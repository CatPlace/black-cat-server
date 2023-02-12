package com.spring.blackcat.user;

import com.spring.blackcat.common.response.ResponseDto;
import com.spring.blackcat.common.response.ResponseUtil;
import com.spring.blackcat.common.security.interceptor.UserId;
import com.spring.blackcat.user.dto.AdditionalInfoReqDto;
import com.spring.blackcat.user.dto.ChangeAddressReqDto;
import com.spring.blackcat.user.dto.ChangeNicknameReqDto;
import com.spring.blackcat.user.dto.UserLoginReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/v1/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseDto login(@RequestBody @Valid UserLoginReqDto userJoinReqDto) {
        return ResponseUtil.SUCCESS("로그인 성공", userService.login(userJoinReqDto));
    }

    @PatchMapping("/additional-info")
    public ResponseDto addAdditionalInfo(@RequestPart("userInfo") @Valid AdditionalInfoReqDto additionalInfoReqDto,
                                         @UserId Long userId,
                                         @RequestPart(value = "images", required = false) List<MultipartFile> images
    ) {
        return ResponseUtil.SUCCESS("추가 정보 등록 성공", userService.addAdditionalInfo(additionalInfoReqDto, images, userId));
    }

    @DeleteMapping()
    public ResponseDto deleteUser(@UserId Long userId) {
        return ResponseUtil.SUCCESS("회원탈퇴 성공", userService.deleteUser(userId));
    }

    @PatchMapping("/tattooist-role")
    public ResponseDto changeRole(@UserId Long userId) {
        return ResponseUtil.SUCCESS("타투이스트로 승급 성공", userService.changeRoleToTattooist(userId));
    }

    @PostMapping("/nickname")
    public ResponseDto changeNickname(ChangeNicknameReqDto changeNicknameReqDto, @UserId Long userId) {
        return ResponseUtil.SUCCESS("닉네임 변경 성공", userService.changeNickname(changeNicknameReqDto, userId));
    }

    @PostMapping("/address")
    public ResponseDto changeAddress(ChangeAddressReqDto changeAddressReqDto, @UserId Long userId) {
        return ResponseUtil.SUCCESS("주소 변경 성공", userService.changeAddress(changeAddressReqDto, userId));
    }
}
