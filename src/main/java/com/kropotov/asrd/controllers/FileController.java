package com.kropotov.asrd.controllers;

import com.kropotov.asrd.services.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.nio.file.Files;

@RequiredArgsConstructor
@Controller
@RequestMapping("/files")
public class FileController {
    private final StorageService storageService;

    @GetMapping(value = "/{path}/{filename}")
    public ResponseEntity<byte[]> redirectToGetFile(@PathVariable String path, @PathVariable("filename") String filename) {
        String extension = filename.substring(filename.lastIndexOf('.') + 1);

        HttpHeaders headers = new HttpHeaders();

        switch (extension) {
            case "pdf":
                headers.setContentType(MediaType.APPLICATION_PDF);
                break;
            case "png":
                headers.setContentType(MediaType.IMAGE_PNG);
                break;
            case "jpg":
                headers.setContentType(MediaType.IMAGE_JPEG);
                break;
            case "jpeg":
                headers.setContentType(MediaType.IMAGE_JPEG);
                break;
            default:
                Resource file = storageService.loadAsResource("invoices", filename);
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                headers.set(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + file.getFilename() + "\"");
        }
        try {
            return new ResponseEntity<>(Files.readAllBytes(storageService.load("invoices", filename)),
                    headers, HttpStatus.OK);
        } catch (IOException e) {
            throw new RuntimeException();
        }

    }
}
