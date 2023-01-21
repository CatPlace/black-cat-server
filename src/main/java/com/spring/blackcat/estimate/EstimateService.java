package com.spring.blackcat.estimate;

import com.spring.blackcat.estimate.dto.CreateUserEstimateReqDto;
import com.spring.blackcat.estimate.dto.CreateUserEstimateResDto;
import com.spring.blackcat.estimate.dto.GetUserEstimateResDto;

public interface EstimateService {
    CreateUserEstimateResDto createUserEstimate(CreateUserEstimateReqDto createUserEstimateReqDto, Long userId);

    GetUserEstimateResDto getUserEstimate(Long userId);
}
