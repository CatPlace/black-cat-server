package com.spring.blackcat.user;

import com.spring.blackcat.user.dto.UserJoinReqDto;
import com.spring.blackcat.user.dto.UserJoinResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users/join")
    public UserJoinResDto join(@RequestBody UserJoinReqDto userJoinReqDto) {
        return userService.join(userJoinReqDto);
    }

}
