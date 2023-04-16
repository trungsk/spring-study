package com.demo.springnguyenduchoang.service;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface IStorageService {
    String storeFile(MultipartFile file);
    Stream<Path> loadAllFile(); // load all file in folder
    byte[] readFileContent(String fileName);
    int deleteAllFiles();
}
