package com.health.ge.jw.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;

@RestController
@RequestMapping("/api/s3")
public class S3TestController {

    private final S3Client s3Client;
    
    @Value("${aws.s3.bucketName}")
    private String bucketName;

    public S3TestController(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @GetMapping("/test-connection")
    public ResponseEntity<String> testS3Connection() {
        try {
            ListBucketsResponse response = s3Client.listBuckets();
            return ResponseEntity.ok("✅ S3 Connection successful! Found " + response.buckets().size() + " buckets");
        } catch (Exception e) {
            return ResponseEntity.ok("❌ S3 Connection failed: " + e.getMessage());
        }
    }

    @GetMapping("/bucket-exists")
    public ResponseEntity<String> checkBucketExists() {
        try {
            s3Client.headBucket(builder -> builder.bucket(bucketName));
            return ResponseEntity.ok("✅ Bucket '" + bucketName + "' exists and is accessible");
        } catch (Exception e) {
            return ResponseEntity.ok("❌ Bucket '" + bucketName + "' check failed: " + e.getMessage());
        }
    }
}