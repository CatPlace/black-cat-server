package com.spring.blackcat.user;

import com.spring.blackcat.user.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    UserLoginResDto login(UserLoginReqDto userJoinReqDto);

    AdditionalInfoResDto addAdditionalInfo(AdditionalInfoReqDto additionalInfoReqDto, List<MultipartFile> images, Long userId);

    CreateTattooistResDto createTattooist(CreateTattooistReqDto createTattooistReqDto, Long userId);

    DeleteUserResDto deleteUser(Long userId);

    ChangeRoleResDto changeRoleToTattooist(Long userId);

    ChangeNicknameResDto changeNickname(ChangeNicknameReqDto changeNicknameReqDto, Long userId);

    ChangeAddressResDto changeAddress(ChangeAddressReqDto changeAddressReqDto, Long userId);
}
