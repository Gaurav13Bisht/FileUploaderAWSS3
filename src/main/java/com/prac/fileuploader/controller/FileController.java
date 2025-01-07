package com.prac.fileuploader.controller;

import com.prac.fileuploader.Service.S3UploaderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileController {

    public final S3UploaderServiceImpl s3UploaderService;

    @Autowired
    public FileController(S3UploaderServiceImpl s3UploaderService){
        this.s3UploaderService = s3UploaderService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam MultipartFile file){
        return ResponseEntity.ok(s3UploaderService.uploadFile(file));
    }
}