package com.spring.blackcat.common;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class InitController {

    private final InitService initService;

    private boolean isFirstRequest = true;


    @GetMapping("/init")
    public String init() {
        if (isFirstRequest) {
            isFirstRequest = false;

            initService.initAddress();
            initService.initUser();
            initService.initCategory();
            initService.initTattoo();
            initService.initMagazine();
            initService.initLikes();

            return "Completed successfully";
        } else {
            return "Initial data already exists.";
        }
    }
}
