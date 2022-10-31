package com.spring.blackcat.user;

import com.spring.blackcat.user.dto.UserLoginReqDto;
import com.spring.blackcat.user.dto.UserLoginResDto;
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
}
