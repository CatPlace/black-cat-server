package com.spring.blackcat.estimate;

import com.spring.blackcat.common.response.ResponseDto;
import com.spring.blackcat.common.response.ResponseUtil;
import com.spring.blackcat.common.security.interceptor.UserId;
import com.spring.blackcat.estimate.dto.CreateUserEstimateReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/estimate")
public class EstimateController {
    private final EstimateService estimateService;

    @PostMapping()
    public ResponseDto createUserEstimate(@RequestBody CreateUserEstimateReqDto createUserEstimateReqDto, @UserId Long userId) {
        return ResponseUtil.SUCCESS("견적서 등록 성공", this.estimateService.createUserEstimate(createUserEstimateReqDto, userId));
    }

    @GetMapping("tattooists/{tattooistId}")
    public ResponseDto getUserEstimate(@UserId Long tattooistId) {
        return ResponseUtil.SUCCESS("견적서 조회 성공", this.estimateService.getUserEstimate(tattooistId));
    }
}
