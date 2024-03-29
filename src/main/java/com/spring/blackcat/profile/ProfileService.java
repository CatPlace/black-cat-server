package com.spring.blackcat.profile;

import com.spring.blackcat.profile.dto.GetProfileResDto;
import com.spring.blackcat.profile.dto.UpsertProfileReqDto;
import com.spring.blackcat.profile.dto.UpsertProfileResDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProfileService {
    UpsertProfileResDto upsertTattooistProfile(Long userId, UpsertProfileReqDto upsertProfileReqDto, List<MultipartFile> images);

    GetProfileResDto getTattooistProfile(Long tattooistId);
}
