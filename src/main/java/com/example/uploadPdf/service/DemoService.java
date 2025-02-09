package com.example.uploadPdf.service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;

@Slf4j
@Service
public class DemoService {

    private final Resource flyer = new ClassPathResource("pdf/flyer.pdf");

    public byte[] getFlyer() throws IOException {
        try {
            File file = flyer.getFile();
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            log.error("File not found or cannot be read", e);
            return new byte[0];
        }
    }

}