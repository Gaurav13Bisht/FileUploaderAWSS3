package com.prac.fileuploader.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.prac.fileuploader.config.S3Config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
@Slf4j
public class S3UploaderServiceImpl implements UploaderService{

    @Autowired
    private AmazonS3 s3Client;

    @Value("${app.s3.bucket}")
    private String s3BucketName;

    @Override
    public String uploadFile(MultipartFile file) {
        final String ogFileName = file.getOriginalFilename();
        final String fileNameS3 = UUID.randomUUID().toString() + "." + ogFileName.split("\\.")[1];

        try{
            s3Client.putObject(s3BucketName, fileNameS3, file.getInputStream(), new ObjectMetadata());
        }
        catch (Exception ex){
//            log.info("Failed Upload");
            System.out.println("Failed Upload");
        }

        return fileNameS3;
    }
}
