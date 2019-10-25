package com.onyourmind.OnYourMind.controller;

import com.onyourmind.OnYourMind.service.filestore.FileStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController(value = "/api/images")
public class FileStoreController {

    @Autowired
    private FileStoreService fileStoreService;

    @PostMapping
    public ResponseEntity uploadUserProfileImage(@RequestParam("file") MultipartFile file) {
        fileStoreService.saveImage(file);
        return ResponseEntity.ok().build();
    }
}
