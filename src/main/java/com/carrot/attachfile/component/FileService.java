package com.carrot.attachfile.component;

import org.springframework.stereotype.Component;

public interface FileService {

    String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception;

    void deleteFile(String filePath) throws Exception;
}
