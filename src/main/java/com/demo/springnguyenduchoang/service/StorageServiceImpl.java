package com.demo.springnguyenduchoang.service;

import com.demo.Constant;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class StorageServiceImpl implements IStorageService {
    private final Path storageFolder = Paths.get("uploads"); // var refers to the folder contains images

    // constructor's invoked when this bean is injected, once
    public StorageServiceImpl() {
        try {
            Files.createDirectories(storageFolder);
        } catch (IOException e) {
            throw new RuntimeException("cannot init storage folder", e);
        }
    }

    private boolean isImageFile(MultipartFile file) {
        // using FileNameUtils from library common-io to check the file is an image by checking the extension
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        assert extension != null;
        return Arrays.asList(
                        new String[]{Constant.FILE.PNG_EXTENSION, Constant.FILE.JPG_EXTENSION, Constant.FILE.JPEG_EXTENSION})
                .contains(extension.trim().toLowerCase());
    }

    @Override
    public String storeFile(MultipartFile file) {
        try {
            // check if file is empty
            System.out.println("store file");
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file");
            }
            // check if file is image
            if (!isImageFile(file)) {
                throw new RuntimeException("Can only upload image");
            }
            // check if file is too large
            float fileSizeInMB = file.getSize() / 1_000_000.0f; // technique to get file size in MB
            if (fileSizeInMB > 0.5f) { // must be lesser or equal 5MB
                throw new RuntimeException("File must be less than or equal to 5MB");
            }
            /*
            file must be renamed, why?
            because we cannot know the name of the file we're goin' to get
            if that file has the name that has already been in our database,
            overwriting or conflict would happen n' it ain't what we expect.
            So, the solution is we have to generate a rule to rename n' name the file we get before saving
             */
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            String generatedFileName = UUID.randomUUID() + "." + extension;
            Path destPath = this.storageFolder.resolve(Paths.get(generatedFileName)).normalize().toAbsolutePath();
            if (!destPath.getParent().equals(this.storageFolder.toAbsolutePath())) {
                throw new RuntimeException("Cannot store file outside the current directory");
            }
            // copy file into the destination
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destPath, StandardCopyOption.REPLACE_EXISTING);
            }
            return generatedFileName;
        } catch (IOException e) {
            throw new RuntimeException("Cannot store file", e);
        }
    }

    /**
     * logic code can be searched by "load file from folder using stream"
     *
     * @return
     */
    @Override
    public Stream<Path> loadAllFile() {
        try {
            return Files.walk(this.storageFolder, 1) // 1 means this just browse the current folder (level 1)
                    .filter(path -> !path.equals(this.storageFolder) && !path.toString().contains("._"))
                    .map(this.storageFolder::relativize);
        } catch (IOException e) {
            throw new RuntimeException("failed to load stored files ", e);
        }
    }

    /**
     * this method return an arr of byte from a file
     * the logic code can be searched easily on the internet
     *
     * @param fileName
     * @return
     */
    @Override
    public byte[] readFileContent(String fileName) {
        try {
            Path file = storageFolder.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return StreamUtils.copyToByteArray(resource.getInputStream());
            } else {
                throw new RuntimeException("Could not read file " + fileName);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteAllFiles() {
        return 0;
    }
}
