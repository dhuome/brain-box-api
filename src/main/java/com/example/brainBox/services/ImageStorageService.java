package com.example.brainBox.services;

import com.example.brainBox.dtos.ImageResponse;
import com.example.brainBox.util.ImageUtil;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.StatObjectResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ImageStorageService {
    private final MinioClient minioClient;

    @Value("${minio.bucket.name}")
    private String bucketName;

    @Value("${minio.url}")
    private String minioUrl;

    @SneakyThrows
    public String uploadImage(MultipartFile file) {
        String fileName = generateFileName(file);
        InputStream is = file.getInputStream();
        minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(fileName).stream(is, file.getSize(), -1).contentType(file.getContentType()).build());
        return minioUrl + "/" + bucketName + "/" + fileName;
    }

    @SneakyThrows
    @Cacheable(value = "imageCache")
    public ImageResponse getImage(String imageUrl) {
        String[] parts = imageUrl.replace(minioUrl + "/", "").split("/", 2);
        String bucketName = parts[0];
        String objectName = parts[1];

        StatObjectResponse stat = minioClient.statObject(io.minio.StatObjectArgs.builder().bucket(bucketName).object(objectName).build());

        InputStream stream = minioClient.getObject(io.minio.GetObjectArgs.builder().bucket(bucketName).object(objectName).build());

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = stream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, length);
        }

        String base64Image = ImageUtil.convertImageToBase64(byteArrayOutputStream.toByteArray());

        return new ImageResponse(base64Image, stat.contentType(), stat.size(), stat.lastModified());
    }

    @SneakyThrows
    public void deleteImage(String imageUrl) {
        String[] parts = imageUrl.replace(minioUrl + "/", "").split("/", 2);
        String bucketName = parts[0];
        String objectName = parts[1];

        minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
    }

    private String generateFileName(MultipartFile file) {
        return new Date().getTime() + "-" + Objects.requireNonNull(file.getOriginalFilename()).replace(" ", "_");
    }
}
