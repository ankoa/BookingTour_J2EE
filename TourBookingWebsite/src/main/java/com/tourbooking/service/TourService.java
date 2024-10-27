package com.tourbooking.service;

import com.tourbooking.dto.response.TourTimeResponse;
import com.tourbooking.model.Tour;
import com.tourbooking.model.TourImage;
import com.tourbooking.model.TourTime;
import com.tourbooking.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TourService {

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private TourTimeService tourTimeService;

    // Lấy tất cả các tour
    public List<Tour> getAllTours() {
        return tourRepository.findAll();
    }

    // Lấy tour theo ID
    public Tour getTourById(String id) {
        return tourRepository.findById(Integer.parseInt(id)).orElse(null);
    }
    
    public Optional<Tour> getTourById(int id) {
        return tourRepository.findById(id);
    }

    // Thêm tour mới
    public Tour addTour(Tour tour) {
        return tourRepository.save(tour);
    }

    // Cập nhật tour
    public Tour updateTour(int id, Tour tour) {
        if (tourRepository.existsById(id)) {
            tour.setTourId(id);
            return tourRepository.save(tour);
        }
        return null;
    }

    // Xóa tour
    public void deleteTour(int id) {
        tourRepository.deleteById(id);
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

    public List<String> getListImageUrl(String id){
        Tour tour = tourRepository.findById(Integer.parseInt(id)).orElse(null);
        return tour.getTourImages() != null ?
                tour.getTourImages().stream()
                        .map(TourImage::getImageUrl)  // Lấy thuộc tính imageUrl
                        .filter(Objects::nonNull)  // Lọc bỏ các giá trị null
                        .collect(Collectors.toList())
                : new ArrayList<>();
    }
    public List<TourTimeResponse> getTourTimeResponseById(String tourId) {
        // Lấy tour times từ repository
        Tour tour = tourRepository.findById(Integer.parseInt(tourId)).orElse(null);

        //Xuat list tourTimes roi map sang tourTimeResponse
        Set<TourTime> tourTimes=tour.getTourTimes();

        List<TourTimeResponse> monthMap = new ArrayList<>();
        for (TourTime tourTime : tourTimes) {

            int reservedCount=tourTimeService.getReservedCount(tourTime);

            TourTimeResponse tourTimeResponse = new TourTimeResponse(tourTime,(tourTime.getQuantity()-reservedCount));
            monthMap.add(tourTimeResponse);
        }
        Collections.sort(monthMap, (t1, t2) -> t1.getDepartureTime().compareTo(t2.getDepartureTime()));

        return monthMap;
    }


}
