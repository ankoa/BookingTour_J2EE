package com.tourbooking.service;

import com.tourbooking.dto.TourTimeResponse;
import com.tourbooking.model.Booking;
import com.tourbooking.model.Tour;
import com.tourbooking.model.TourTime;
import com.tourbooking.repository.TourRepository;
import com.tourbooking.repository.TourTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class TourTimeService {

    @Autowired
    private TourTimeRepository tourTimeRepository;

    @Autowired
    private TourRepository tourRepository;

    public List<TourTime> getAllTourTimes() {
        return tourTimeRepository.findAll();
    }

    public TourTime getTourById(String id) {
        return tourTimeRepository.findById(Integer.parseInt(id)).orElse(null);
    }



    public List<Map<String, Object>> groupTourTimesByMonth(String tourId) {
        // Lấy tất cả tour times từ repository
        Tour tour = tourRepository.findById(Integer.parseInt(tourId)).orElse(null);
        if( tour == null) return null;
        Set<TourTime> tourTimes=tour.getTourTimes();
        List<Map<String, Object>> groupedTourTimes = new ArrayList<>();


        // Tạo Map
        // Nhóm tour time theo tháng
        // Object bao gồm TourTime
        Map<Integer, List<TourTimeResponse>> monthMap = new HashMap<>();
        for (TourTime tourTime : tourTimes) {
            LocalDateTime departureTime = tourTime.getDepartureTime();
            int month = departureTime.getMonthValue();

            // Nếu tháng chưa có trong bản đồ, khởi tạo danh sách mới
            monthMap.putIfAbsent(month, new ArrayList<>());

            int reservedCount=0;
            Set<Booking> bookings =tourTime.getBookings();
            for(Booking booking : bookings){
                reservedCount+=booking.getAdultCount()+booking.getChildCount();
            }
            TourTimeResponse tourTimeResponse = new TourTimeResponse(tourTime,(tourTime.getQuantity()-reservedCount));

            monthMap.get(month).add(tourTimeResponse);
        }

        //Bỏ Map vào List
        // Chuyển đổi bản đồ thành danh sách các bản đồ
        for (Map.Entry<Integer, List<TourTimeResponse>> entry : monthMap.entrySet()) {
            Map<String, Object> monthEntry = new HashMap<>();
            monthEntry.put("month", entry.getKey());
            monthEntry.put("data", entry.getValue());
            groupedTourTimes.add(monthEntry);
        }
        return groupedTourTimes;
    }
}
