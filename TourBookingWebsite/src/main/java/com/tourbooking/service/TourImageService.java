package com.tourbooking.service;

import com.tourbooking.model.TourImage;
import com.tourbooking.repository.TourImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class TourImageService {

    private final TourImageRepository tourImageRepository;
    private final CloudinaryService cloudinaryService;

    @Autowired
    public TourImageService(TourImageRepository tourImageRepository, CloudinaryService cloudinaryService) {
        this.tourImageRepository = tourImageRepository;
        this.cloudinaryService = cloudinaryService;
    }

    /**
     * Lấy danh sách ảnh của tour
     * 
     * @param tourId ID của tour
     * @return Danh sách các ảnh của tour
     */
    public List<TourImage> getTourImages(int tourId) {
        return tourImageRepository.findByTourId(tourId);
    }

    /**
     * Lưu ảnh vào cơ sở dữ liệu và upload lên Cloudinary
     * 
     * @param tourImage đối tượng TourImage chứa thông tin ảnh
     * @return true nếu lưu ảnh thành công, false nếu có lỗi
     */
    public boolean save(TourImage tourImage) {
        try {
            // Lưu thông tin ảnh vào cơ sở dữ liệu
            tourImageRepository.save(tourImage);
            return true;  // Nếu lưu thành công, trả về true
        } catch (Exception e) {
            // Nếu có lỗi trong quá trình lưu, in lỗi ra console và trả về false
            System.err.println("Error saving image: " + e.getMessage());
            return false;
        }
    }
 // Cập nhật thông tin ảnh
    public void updateImage(int imageId, String imageUrl, int status) {
        Optional<TourImage> existingImage = tourImageRepository.findById(imageId);
        if (existingImage.isPresent()) {
        	TourImage image = existingImage.get();
            image.setImageUrl(imageUrl);
            image.setStatus(status);
            tourImageRepository.save(image);
        }
    }
    /**
     * Xóa ảnh của tour từ cơ sở dữ liệu
     * 
     * @param imageId ID của ảnh cần xóa
     * @return true nếu xóa thành công, false nếu có lỗi
     */
    public boolean deleteImage(int imageId) {
        try {
            // Tìm ảnh theo ID và xóa
            TourImage tourImage = tourImageRepository.findById(imageId).orElse(null);
            if (tourImage != null) {
                tourImageRepository.delete(tourImage);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println("Error deleting image: " + e.getMessage());
            return false;
        }
    }
}
