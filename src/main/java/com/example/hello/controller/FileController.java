package com.example.hello.controller;

import com.example.hello.common.Result;
import com.example.hello.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file) {
        try {
            String url = fileService.upload(file);
            return Result.success(url);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
