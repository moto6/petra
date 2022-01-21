package com.board.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping
    public String main() {
        return "web/main";
    }

    @GetMapping
    public String login() {
        return "web/login";
    }

}
