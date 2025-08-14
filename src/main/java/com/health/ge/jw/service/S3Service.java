package com.health.ge.jw.service;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.nio.file.Paths;

@Service
public class S3Service {

    private final S3Client s3Client;

    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public void uploadFile(String bucketName, String keyName, String filePath) {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(keyName)
                .build();

        s3Client.putObject(request, Paths.get(filePath));
        System.out.println("✅ File uploaded: " + keyName);
    }

    public void downloadFile(String bucketName, String keyName, String destinationPath) {
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(keyName)
                .build();

        s3Client.getObject(request, Paths.get(destinationPath));
        System.out.println("✅ File downloaded: " + destinationPath);
    }
}