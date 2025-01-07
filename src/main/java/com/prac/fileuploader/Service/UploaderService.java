package com.prac.fileuploader.Service;

import org.springframework.web.multipart.MultipartFile;

public interface UploaderService {
    public String uploadFile(MultipartFile file);
    public String presignedUrl(String s3fileName);
    public byte[] downloadFile(String s3fileName);
}