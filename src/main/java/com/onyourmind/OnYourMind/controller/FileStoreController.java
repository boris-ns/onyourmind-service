package com.onyourmind.OnYourMind.controller;

import com.onyourmind.OnYourMind.service.filestore.FileStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/images")
public class FileStoreController {

    @Autowired
    private FileStoreService fileStoreService;

    @PostMapping(value = "/users", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity uploadUserProfileImage(@RequestParam("file") MultipartFile file) {
        fileStoreService.saveImage(file);
        return ResponseEntity.ok().build();
    }

}
