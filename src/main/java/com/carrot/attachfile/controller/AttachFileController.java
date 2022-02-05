package com.carrot.attachfile.controller;

import com.carrot.attachfile.service.AttachFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class AttachFileController {

    private final AttachFileService fileService;

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
        fileService.simpleFileUpload(file);
        return "redirect:/success";
    }

    @PostMapping(path = "/upload")
    public ResponseEntity<String> uploadFileHandler(@RequestParam("file") MultipartFile file) {
        return file.isEmpty() ?
                new ResponseEntity<String>(HttpStatus.NOT_FOUND) : new ResponseEntity<String>(HttpStatus.OK);
    }
}
