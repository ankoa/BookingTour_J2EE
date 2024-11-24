package com.tourbooking.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Map;

@Component
public class CloudinaryTest {

    @Autowired
    private Cloudinary cloudinary;

    public void testConnection() {
        try {
            Map<String, Object> response = cloudinary.api().ping(ObjectUtils.emptyMap());
            System.out.println("Connection successful: " + response);
        } catch (Exception e) {
            System.err.println("Connection failed: " + e.getMessage());
        }
    }
    public void testConnectionUpload() {
        try {
            File file = new File("C:/Users/Admin/Pictures/Screenshots/Screenshot 2024-07-29 225707.png");
            Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
            System.out.println("Upload successful: " + uploadResult.get("url"));
        } catch (Exception e) {
            System.err.println("Upload failed: " + e.getMessage());
        }
    }
}
