package com.spring.blackcat.user;

import com.spring.blackcat.common.security.interceptor.UserId;
import com.spring.blackcat.user.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public UserLoginResDto login(@RequestBody UserLoginReqDto userJoinReqDto) {
        return userService.login(userJoinReqDto);
    }

    @PostMapping("/role")
    public ChangeRoleResDto changeRole(ChangeRoleReqDto changeRoleReqDto, @UserId Long userId) {
        return userService.changeRole(changeRoleReqDto, userId);
    }

    @PostMapping("/nickname")
    public ChangeNicknameResDto changeNickname(ChangeNicknameReqDto changeNicknameReqDto, @UserId Long userId) {
        return userService.changeNickname(changeNicknameReqDto, userId);
    }

    @PostMapping("/address")
    public ChangeAddressResDto changeAddress(ChangeAddressReqDto changeAddressReqDto, @UserId Long userId) {
        return userService.changeAddress(changeAddressReqDto, userId);
    }
}
