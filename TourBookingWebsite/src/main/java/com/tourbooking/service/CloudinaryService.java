package com.tourbooking.service;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    // Phương thức tải ảnh lên Cloudinary và trả về URL
    public String uploadImage(File file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.asMap("resource_type", "auto"));
        return (String) uploadResult.get("secure_url"); // Trả về URL của hình ảnh đã được tải lên
    }
}
