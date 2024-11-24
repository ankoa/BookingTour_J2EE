package com.tourbooking.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;  // Cloudinary đã cấu hình trong ứng dụng

    /**
     * Upload file lên Cloudinary và trả về URL
     * 
     * @param file MultipartFile từ người dùng
     * @return URL của ảnh
     * @throws IOException nếu upload thất bại
     */
    public String uploadImage(MultipartFile file) throws IOException {
        // Upload file lên Cloudinary
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), 
                ObjectUtils.asMap("folder", "tour_images")); // Lưu ảnh vào thư mục "tour_images"
        return uploadResult.get("secure_url").toString(); // Trả về URL ảnh
    }
}
