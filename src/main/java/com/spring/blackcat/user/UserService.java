package com.spring.blackcat.user;

import com.spring.blackcat.user.dto.*;

public interface UserService {

    UserLoginResDto login(UserLoginReqDto userJoinReqDto);

    AdditionalInfoResDto addAdditionalInfo(AdditionalInfoReqDto additionalInfoReqDto, Long userId);

    CreateTattooistResDto createTattooist(CreateTattooistReqDto createTattooistReqDto, Long userId);

    DeleteUserResDto deleteUser(Long userId);

    ChangeRoleResDto changeRole(ChangeRoleReqDto changeRoleReqDto, Long userId);

    ChangeNicknameResDto changeNickname(ChangeNicknameReqDto changeNicknameReqDto, Long userId);

    ChangeAddressResDto changeAddress(ChangeAddressReqDto changeAddressReqDto, Long userId);
}
