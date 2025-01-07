package com.prac.fileuploader.controller;

import com.prac.fileuploader.Service.S3UploaderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileController {

    public final S3UploaderServiceImpl s3UploaderService;

    @Autowired
    public FileController(S3UploaderServiceImpl s3UploaderService) {
        this.s3UploaderService = s3UploaderService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam MultipartFile file) {
        return ResponseEntity.ok(s3UploaderService.uploadFile(file));
    }

    @GetMapping("/download")
    public ResponseEntity<ByteArrayResource> downloadImage(@RequestParam String s3fileName) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + s3fileName + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new ByteArrayResource(s3UploaderService.downloadFile(s3fileName)));

    }
}