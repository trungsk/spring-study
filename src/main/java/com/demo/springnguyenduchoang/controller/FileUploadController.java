package com.demo.springnguyenduchoang.controller;

import com.demo.springnguyenduchoang.model.ResponseObject;
import com.demo.springnguyenduchoang.service.IStorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

// this controller receive a file/image from client
@Controller
@RequestMapping("/api/v1")
public class FileUploadController {
    private final IStorageService iStorageService;

    public FileUploadController(IStorageService iStorageService) {
        this.iStorageService = iStorageService;
    }

    // the word "file" in @RequestParam must be the same with key name in body/form-data/key in postman
    @PostMapping("/upload-file")
    public ResponseEntity<ResponseObject> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String generatedFileName = iStorageService.storeFile(file);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("OK", "upload file successfully", generatedFileName)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("FAILED", "upload file failed", "")
            );
        }
    }

    // get file url
    // @PathVariable will take the variable in url then assign to the argument
    @GetMapping("/files/{fileName:.+}")
    public ResponseEntity<byte[]> readDetailFile(@PathVariable String fileName) {
        try {
            byte[] bytes = iStorageService.readFileContent(fileName);
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(bytes);

        } catch (Exception exception) {
            return ResponseEntity.noContent().build();
        }
    }

    // get all file uploaded
    @GetMapping("/all-files")
    public ResponseEntity<ResponseObject> getAllUploadedFiles() {
        try {
            // the code below convert file name to url then send request to readDetailFile
            List<String> urls = iStorageService.loadAllFile()
                    .map(path -> {
                        return MvcUriComponentsBuilder
                                .fromMethodName(FileUploadController.class,
                                        "readDetailFile",
                                        path.getFileName().toString()).build().toUri().toString();
                    })
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new ResponseObject("OK", "Get list of files successfully", urls));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseObject("FAILED", "Get list of files failed", ""));
        }
    }
}
