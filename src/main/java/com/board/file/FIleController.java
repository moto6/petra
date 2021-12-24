package com.board.file;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class FIleController {

    private FileService fileService;

    @GetMapping
    public String upload(){
        return "upload";
    }
}
