package com.spring.blackcat.user;

import com.spring.blackcat.user.dto.UserJoinReqDto;
import com.spring.blackcat.user.dto.UserJoinResDto;

public interface UserService {

    UserJoinResDto join(UserJoinReqDto userJoinReqDto);
}
