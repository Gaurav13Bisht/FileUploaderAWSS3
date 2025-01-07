package com.prac.fileuploader.Service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.Date;
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
        final String fileNameOG = file.getOriginalFilename();
        final String fileNameS3 = UUID.randomUUID().toString() + "." + fileNameOG.split("\\.")[1];

        try{
            s3Client.putObject(s3BucketName, fileNameS3, file.getInputStream(), new ObjectMetadata());
        }
        catch (Exception ex){
//            log.info("Failed Upload");
            System.out.println("Failed Upload");
        }

        return presignedUrl(fileNameS3);
    }

    @Override
    public String presignedUrl(String fileNameS3) {

        Date expirationDate = new Date();
        long time = expirationDate.getTime();
        expirationDate.setTime(time + 40000);
        URL url = s3Client.generatePresignedUrl(s3BucketName, fileNameS3, expirationDate, HttpMethod.GET);
        return url.toString();
    }

    @Override
    public byte[] downloadFile(String s3fileName) {
        try {
            S3Object object = s3Client.getObject(s3BucketName, s3fileName);
            S3ObjectInputStream objectContent = object.getObjectContent();
            return objectContent.readAllBytes();
        }
        catch (Exception ex){
            System.out.println("File not found !!");
        }
        return null;
    }
}
