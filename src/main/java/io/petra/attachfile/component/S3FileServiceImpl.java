package io.petra.attachfile.component;

import org.springframework.stereotype.Component;

@Component
public class S3FileServiceImpl implements FileService{
    @Override
    public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception {
        return null;
        //todo : s3에다가 파일을 업로드
    }

    @Override
    public void deleteFile(String filePath) throws Exception {
        // s3 에 있는 파일 삭제
    }
}
