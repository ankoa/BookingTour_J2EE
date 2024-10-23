package com.tourbooking.service;

import com.tourbooking.model.TourImage;
import com.tourbooking.repository.TourImageRepository; // Giả sử bạn có một repository để lưu hình ảnh
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class TourImageService {

    @Autowired
    private TourImageRepository tourImageRepository; // Khai báo repository

    // Phương thức lưu ảnh
    public String saveImage(MultipartFile imageFile) {
        String uploadDir = "uploads/"; // Thư mục lưu ảnh trong dự án

        // Tạo thư mục nếu chưa tồn tại
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs(); // Tạo thư mục nếu không tồn tại
        }

        String fileName = imageFile.getOriginalFilename(); // Lấy tên file
        File file = new File(dir, fileName); // Kết hợp thư mục và tên file
        try {
            // Lưu file vào hệ thống
            imageFile.transferTo(file);
            return file.getAbsolutePath(); // Trả về đường dẫn tuyệt đối
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Hoặc xử lý ngoại lệ phù hợp
        }
    }

    // Phương thức thêm hình ảnh vào cơ sở dữ liệu
    public void addTourImage(TourImage tourImage) {
        tourImageRepository.save(tourImage); // Giả sử phương thức save() sẽ lưu tourImage vào cơ sở dữ liệu
    }

    // Các phương thức khác liên quan đến hình ảnh...
}
