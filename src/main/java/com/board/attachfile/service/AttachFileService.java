package com.board.attachfile.service;

import com.board.attachfile.entity.AttachFile;
import com.board.attachfile.repository.AttachFileRepository;
import com.board.common.fileservice.FileService;
import com.board.exception.AttachFileStorageException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class AttachFileService {

    private final FileService fileService;
    private final AttachFileRepository attachFileRepository;
    Logger log = LoggerFactory.getLogger(getClass());
    @Value("${attachFileLocation}")
    private String attachFileLocation;


    @Value("${user.home}")
    private String uploadDir;


    public void simpleFileUpload(MultipartFile multipartFile) {
        Path serverPath = Paths.get(
                uploadDir +
                        File.separator +
                        StringUtils.cleanPath(multipartFile.getOriginalFilename()));

        try {
            Files.copy(multipartFile.getInputStream(), serverPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("fail to store file : name={}, exception={}",
                      multipartFile.getOriginalFilename(),
                      e.getMessage());
            throw new AttachFileStorageException("fail to store file");
        }
    }


    public void saveAttach(AttachFile attachFileEntity, MultipartFile uploadedFile) throws Exception {

        String originalName = uploadedFile.getOriginalFilename();
        String validName = "";


        if (!StringUtils.hasLength(originalName)) {
            validName = fileService.uploadFile(attachFileLocation, originalName, uploadedFile.getBytes());
        }

        //
        attachFileEntity.updateAttachFile(originalName, validName);
        attachFileRepository.save(attachFileEntity);
    }


    public void updateAttach(Long attachFileId, MultipartFile itemImgFile) throws Exception {

        if (!itemImgFile.isEmpty()) { // 변경점 없으면 그대로 통과
            AttachFile saved = attachFileRepository
                    .findById(attachFileId)
                    .orElseThrow(EntityNotFoundException::new);

            if (!StringUtils.hasLength(saved.getOriginalFileName())) {
                // 삭제는 파일시스템에 저장된 이름으로
                fileService.deleteFile(attachFileLocation + "/" + saved.getVerifiedFileName());
            }


            String originalName = itemImgFile.getOriginalFilename();
            String validName = fileService.uploadFile(attachFileLocation, originalName, itemImgFile.getBytes());
            saved.updateAttachFile(originalName, validName);
        }
    }
}
