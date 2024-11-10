package com.tourbooking.service;

import com.tourbooking.dto.response.TourResponse;
import com.tourbooking.dto.response.TourTimeResponse;
import com.tourbooking.mapper.TourMapper;
import com.tourbooking.mapper.TourTimeMapper;
import com.tourbooking.mapper.TransportMapper;
import com.tourbooking.model.*;
import com.tourbooking.repository.TourRepository;
import com.tourbooking.repository.TourTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class TourService {

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private TourTimeService tourTimeService;

    @Autowired
    TourTimeMapper tourTimeMapper;

    @Autowired
    TourMapper tourMapper;

    @Autowired
    TransportMapper transportMapper;

    @Autowired
    TourTimeRepository tourTimeRepository;

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


    public TourResponse getTourResponse(String id,Integer status) {
        Tour tour = tourRepository.findById(
                Integer.parseInt(id)).orElseThrow(() -> new IllegalArgumentException(
                "Không tìm thấy tour với ID: " + id
        ));

        if (status != null && tour.getStatus() !=status)  return null;
        TourResponse tourResponse = tourMapper.toTourResponse(tour);

        List<TourTimeResponse> tourTimeResponses = new ArrayList<>();
        for (TourTime tourTime : tour.getTourTimes()) {
            if (status != null && tourTime.getStatus() != status) continue;

            TourTimeResponse tourTimeResponse = tourTimeService.toTourTimeResponse(tourTime,status);

            tourTimeResponses.add(tourTimeResponse);
        }
        tourTimeResponses.sort((t1, t2) -> t1.getDepartureTime().compareTo(t2.getDepartureTime()));
        tourResponse.setTourTimesResponse(tourTimeResponses);
        return tourResponse;
    }
}
