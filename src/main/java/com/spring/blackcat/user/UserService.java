package com.spring.blackcat.user;

import com.spring.blackcat.user.dto.UserLoginReqDto;
import com.spring.blackcat.user.dto.UserLoginResDto;

public interface UserService {

    UserLoginResDto login(UserLoginReqDto userJoinReqDto);
}
