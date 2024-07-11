package com.ilevent.ilevent_backend.events.service;

import com.cloudinary.Cloudinary;
import com.ilevent.ilevent_backend.config.CloudinaryConfig;
import org.springframework.stereotype.Service;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploudFile(MultipartFile file) throws IOException {
        Map uploudResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        return uploudResult.get("url").toString();
    }
}
