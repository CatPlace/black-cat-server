package com.spring.blackcat.common.init;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Profile({"LOCAL", "DEV", "PRD"})
public class InitController {

    private final InitService initService;

    @PostMapping("/init")
    public String init() {
        return initService.init() ? "success" : "already exists";
    }
}
