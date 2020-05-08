package com.kropotov.asrd.services;

import com.kropotov.asrd.exceptions.StorageException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface StorageService {

    String store(String path, MultipartFile file);

    String store(String path, String filename, MultipartFile file) throws StorageException;

    Resource loadAsResource(String path, String filename);

    Path load(String path, String filename);

    void delete(String path, String filename);
}
