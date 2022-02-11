package com.carrot.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class WebController {

    @GetMapping()
    public String main() {

        log.info("메인페이지");
        return "web/main";
    }

    @GetMapping("/myLogin")
    public String login() {

        log.info("로그인페이지");
        return "web/login";
    }


}
