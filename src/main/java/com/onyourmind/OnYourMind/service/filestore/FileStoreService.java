package com.onyourmind.OnYourMind.service.filestore;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.onyourmind.OnYourMind.exception.ApiRequestException;
import com.onyourmind.OnYourMind.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class FileStoreService {

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private UserServiceImpl userService;

    public String saveImage(MultipartFile file) {
        try {
            Map result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String path = (String) result.get("url");
            userService.setProfileImage(path);
            return path;
        } catch (IOException e){
            throw new ApiRequestException("There was an error while uploading a profile image.");
        }
    }
}
