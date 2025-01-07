package com.prac.fileuploader.Service;

import org.springframework.web.multipart.MultipartFile;

public interface UploaderService {
    public String uploadFile(MultipartFile file);
}