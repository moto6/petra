package com.carrot.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/my")
@Controller
@RequiredArgsConstructor
public class MyPageController {

    @GetMapping
    public String myPageMain() {

        return "my/myPageMain";
    }

}
