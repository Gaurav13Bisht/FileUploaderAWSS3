package com.prac.fileuploader.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class S3UploaderServiceImpl implements UploaderService{

    @Override
    public String uploadFile(MultipartFile file) {
        return "API is working fine";
    }
}
