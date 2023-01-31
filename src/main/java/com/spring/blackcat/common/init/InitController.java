package com.spring.blackcat.common.init;

import com.spring.blackcat.common.init.dto.InitResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Transactional
@Profile({"LOCAL", "DEV", "PRD"})
public class InitController {

    private final InitService initService;

    private boolean isFirstRequest = true;

    @PostMapping("/init")
    public InitResDto init() {
        if (isFirstRequest) {
            initService.initAddress();
            initService.initUser();
            initService.initCategory();
            initService.initTattoo();
            initService.initMagazine();
            initService.initLikes();
            initService.initImage();
            isFirstRequest = false;
            return new InitResDto("success");
        } else {
            return new InitResDto("already exists");
        }
    }
}
