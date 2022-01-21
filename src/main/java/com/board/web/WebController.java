package com.board.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class WebController {

    @GetMapping()
    public String main() {

        return "web/main";
    }

    @GetMapping("/myLogin")
    public String login() {
        return "web/login";
    }


}
