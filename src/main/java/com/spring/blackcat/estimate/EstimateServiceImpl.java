package com.spring.blackcat.estimate;

import com.spring.blackcat.common.exception.ErrorInfo;
import com.spring.blackcat.common.exception.custom.UserNotFoundException;
import com.spring.blackcat.estimate.dto.CreateUserEstimateReqDto;
import com.spring.blackcat.estimate.dto.CreateUserEstimateResDto;
import com.spring.blackcat.estimate.dto.GetUserEstimateResDto;
import com.spring.blackcat.user.User;
import com.spring.blackcat.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EstimateServiceImpl implements EstimateService {
    private final UserRepository userRepository;
    private final EstimateRepository estimateRepository;

    @Override
    @Transactional
    public CreateUserEstimateResDto createUserEstimate(CreateUserEstimateReqDto createUserEstimateReqDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자 입니다.", ErrorInfo.USER_NOT_FOUND_EXCEPTION));
        Estimate estimate = this.estimateRepository.findByUserId(userId);

        estimate.updateEstimate(createUserEstimateReqDto.getDescription());

        CreateUserEstimateResDto createUserEstimateResDto = new CreateUserEstimateResDto(createUserEstimateReqDto.getDescription());

        return createUserEstimateResDto;
    }

    @Override
    public GetUserEstimateResDto getUserEstimate(Long userId) {
        Estimate estimate = this.estimateRepository.findByUserId(userId);

        GetUserEstimateResDto getUserEstimateResDto = new GetUserEstimateResDto(estimate.getDescription());

        return getUserEstimateResDto;
    }
}
