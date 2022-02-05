package com.carrot.attachfile.component.fileservice;

import org.springframework.stereotype.Service;

@Service
public interface FileService {

    String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception;

    void deleteFile(String filePath) throws Exception;
}
