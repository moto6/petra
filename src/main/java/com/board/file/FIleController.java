package com.board.file;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class FIleController {

    private final FileService fileService;

    @GetMapping("/upload")
    public String upload() {
        return "upload";
    }

    @GetMapping("/success")
    public String success() {
        return "success";
    }

    @PostMapping("/api/v1/file")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        fileService.fileUpload(file);
        return "redirect:/success";
    }
}
