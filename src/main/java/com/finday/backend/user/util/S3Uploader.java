package com.finday.backend.user.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value; // ✅ 정답
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class S3Uploader {

    private final S3Client s3Client;  // ✅ 주입 받음

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    public String uploadFile(MultipartFile file, String dirName) {
        try {
            String fileName = createFileName(file.getOriginalFilename(), dirName);

            // 업로드 요청
            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucket)
                            .key(fileName)
                            .contentType(file.getContentType())
                            .build(),
                    RequestBody.fromBytes(file.getBytes())
            );

            // 반환할 공개 URL
            return "https://" + bucket + ".s3." + region + ".amazonaws.com/" +
                    URLEncoder.encode(fileName, StandardCharsets.UTF_8);

        } catch (IOException e) {
            throw new RuntimeException("파일 업로드 실패", e);
        }
    }

    public static String createFileName(String originalName, String dir) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return dir + "/" + timestamp + "_" + originalName.replaceAll(" ", "_");
    }
}