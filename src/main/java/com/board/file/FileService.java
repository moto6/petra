package com.board.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FileService {

    @Value("${user.home}}")
    private String uploadDir;



}
