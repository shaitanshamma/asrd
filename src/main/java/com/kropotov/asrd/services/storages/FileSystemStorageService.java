package com.kropotov.asrd.services.storages;

import com.kropotov.asrd.exceptions.StorageException;
import com.kropotov.asrd.exceptions.StorageFileNotFoundException;
import com.kropotov.asrd.services.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@Primary
public class FileSystemStorageService implements StorageService {

    @Value("${storage.location}")
    private String storagePath;

    private Path rootLocation;

    @PostConstruct
    public void init() {
        try {
            rootLocation = Paths.get(storagePath);
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            log.error("Ошибка при создании хранилища файло " + rootLocation.toAbsolutePath());
            throw new StorageException("Ошибка при создании хранилища файло " + rootLocation.toAbsolutePath(), e);
        }
    }

    @Override
    public String store(String path, String filename, MultipartFile file) {

        try {
            if (file.isEmpty()) {
                throw new StorageException("Файл " + filename + " пуст");
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Невозможно сохранить файл за пределами текущей директории "
                                + filename);
            }
            // TODO добавить проверку на уникальное имя
            try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(rootLocation.resolve(path))) {
                for (Path child : dirStream) {
                    if (child.getFileName().toString().equals(filename)) {
                        throw new StorageException("Файл с таким именем уже существует " + rootLocation.toAbsolutePath());
                    }
                }
            }
            try {
                    Files.createDirectories(rootLocation.resolve(path));
            } catch (IOException e) {
                log.error("Ошибка при создании хранилища файлов " + rootLocation.toAbsolutePath());
                throw new StorageException("Ошибка при создании хранилища файло " + rootLocation.toAbsolutePath(), e);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, this.rootLocation.resolve(path).resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new StorageException("Ошибка при сохранении файла " + filename, e);
        }
        return filename;
    }

    @Override
    public String store(String path, MultipartFile file) {
        String filename = UUID.randomUUID() + "_" +StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        return store(path, filename, file);
    }

    public Path load(String path, String filename) {
        return rootLocation.resolve(path).resolve(filename);
    }

    @Override
    public Resource loadAsResource(String path, String filename) {
        try {
            Path file = rootLocation.resolve(path).resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                log.error("Файл "+ filename + "в директории" + path + " не найден!");
                throw new StorageFileNotFoundException("Файл "+ filename + "в директории" + path + " не найден!");
            }
        } catch (MalformedURLException e) {
            log.error("Файл "+ filename + "в директории" + path + " не найден!");
            throw new StorageFileNotFoundException("Файл "+ filename + "в директории" + path + " не найден!", e);
        }
    }
}
