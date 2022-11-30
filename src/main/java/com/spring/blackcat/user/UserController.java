package com.spring.blackcat.user;

import com.spring.blackcat.common.response.ResponseDto;
import com.spring.blackcat.common.response.ResponseUtil;
import com.spring.blackcat.common.security.interceptor.UserId;
import com.spring.blackcat.user.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public ResponseDto addAdditionalInfo(@RequestBody @Valid AdditionalInfoReqDto additionalInfoReqDto, @UserId Long userId) {
        return ResponseUtil.SUCCESS("추가 정보 등록 성공", userService.addAdditionalInfo(additionalInfoReqDto, userId));
    }

    @PostMapping("/tattooist")
    public ResponseDto createTattooist(@RequestBody @Valid CreateTattooistReqDto createTattooistReqDto, @UserId Long userId) {
        return ResponseUtil.SUCCESS("타투이스트 등록 성공", userService.createTattooist(createTattooistReqDto, userId));
    }

    @PostMapping("/role")
    public ResponseDto changeRole(ChangeRoleReqDto changeRoleReqDto, @UserId Long userId) {
        return ResponseUtil.SUCCESS("권한 변경 성공", userService.changeRole(changeRoleReqDto, userId));
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
