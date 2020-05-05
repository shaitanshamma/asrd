package com.kropotov.asrd.services.storages;

import com.kropotov.asrd.exceptions.StorageException;
import io.minio.MinioClient;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public class MinIOStorageService {

    private final MinioClient minioClient;

    public MinIOStorageService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public String store(String bucketName, MultipartFile file) {
        try {
            if (!minioClient.bucketExists(bucketName)) {
                minioClient.makeBucket(bucketName);
            }
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            try (InputStream inputStream = file.getInputStream()) {
                minioClient.putObject(bucketName, filename, inputStream, "application/octet-stream");
                System.out.println(minioClient.getObjectUrl(bucketName, filename));
                System.out.println(minioClient.presignedGetObject(bucketName, filename));
                return minioClient.presignedGetObject(bucketName, filename);
            } catch (IOException e) {
                throw new StorageException("Failed to store file " + filename, e);
            }
        } catch (Exception e) {
            throw new StorageException("Failed to store file ", e);
        }
    }
}
