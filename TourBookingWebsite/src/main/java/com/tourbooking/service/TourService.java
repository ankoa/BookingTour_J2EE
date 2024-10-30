package com.tourbooking.service;
import com.tourbooking.model.Tour;
import com.tourbooking.model.TourImage;
import com.tourbooking.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TourService {

    @Autowired
    private TourRepository tourRepository;

    // Lấy tất cả các tour
    public List<Tour> getAllTours() {
        return tourRepository.findAll();
    }

    // Lấy tour theo ID

    public Tour getTourById(String id) {
        return tourRepository.findById(Integer.parseInt(id)).orElse(null);
    }
    
    public Optional<Tour> getTourByIdInt(int id) {
        return tourRepository.findById(id);
    }

    // Thêm tour mới
    public Tour addTour(Tour tour) {
        return tourRepository.save(tour);
    }

    public Tour updateTour(int id, Tour tour) {
        if (tourRepository.existsById(id)) {
            tour.setTourId(id);
            return tourRepository.save(tour); // Trả về đối tượng Tour đã cập nhật
        }
        return null; // Trả về null nếu không tồn tại tour với ID đã cho
    }


    // Xóa tour

    public boolean deleteTour(int id) {
        try {
            tourRepository.deleteTour(id);            
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    private String saveImage(MultipartFile imageFile) {
        String uploadDir = "uploads/"; // Nếu là thư mục trong dự án
        // Hoặc chỉ định đường dẫn tuyệt đối
        // String uploadDir = "/home/user/images/";
        
        String fileName = imageFile.getOriginalFilename(); // Lấy tên file
        File file = new File(uploadDir + fileName);
        try {
            // Lưu file vào hệ thống
            imageFile.transferTo(file);
            return file.getAbsolutePath(); // Trả về đường dẫn tuyệt đối
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Hoặc xử lý ngoại lệ phù hợp
        }
    }
    public List<Tour> searchTours(String searchValue) {
        return tourRepository.searchTours(searchValue);
    }




    public List<String> getListImageUrl(String id){
        Tour tour = tourRepository.findById(Integer.parseInt(id)).orElse(null);
        return tour.getTourImages() != null ?
                tour.getTourImages().stream()
                        .map(TourImage::getImageUrl)  // Lấy thuộc tính imageUrl
                        .filter(Objects::nonNull)  // Lọc bỏ các giá trị null
                        .collect(Collectors.toList())
                : new ArrayList<>();
    }

}
