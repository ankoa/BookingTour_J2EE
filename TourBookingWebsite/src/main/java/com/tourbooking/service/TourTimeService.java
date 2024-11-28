package com.tourbooking.service;

import com.tourbooking.dto.response.TourTimeResponse;
import com.tourbooking.dto.response.TransportResponse;
import com.tourbooking.mapper.TourTimeMapper;
import com.tourbooking.mapper.TransportMapper;
import com.tourbooking.model.*;
import com.tourbooking.repository.TourRepository;
import com.tourbooking.repository.TourTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TourTimeService {

    @Autowired
    private TourTimeRepository tourTimeRepository;

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private TourTimeMapper tourTimeMapper;

    @Autowired
    private TransportMapper transportMapper;

    @Autowired
    private TransportService transportService;

    public int calculateRemainPax(TourTime tourTime) {
        int reservedCount = 0;
        Set<Booking> bookings = tourTime.getBookings();
        for (Booking booking : bookings) {
            reservedCount += booking.getAdultCount() + booking.getChildCount();
        }
        return tourTime.getQuantity() - reservedCount;
    }

    public TourTimeResponse toTourTimeResponse(TourTime tourTime, Integer status) {

        if (status != null && tourTime.getStatus() != status) return null;

        TourTimeResponse tourTimeResponse = tourTimeMapper.toTourTimeResponse(tourTime);

        Date currentDate = new Date();
        if (!tourTime.getDiscounts().isEmpty())
            for (Discount discount : tourTime.getDiscounts()) {
                if (discount.getStartDate() != null)
                    if (!currentDate.after(discount.getStartDate())) continue;
                if (discount.getEndDate() != null)
                    if (!currentDate.before(discount.getEndDate())) continue;
                tourTimeResponse.setIsDiscount(true);
                tourTimeResponse.setDiscountValue(discount.getDiscountValue());
                break;
            }
        else {
            tourTimeResponse.setIsDiscount(false);
            tourTimeResponse.setDiscountValue(0);
        }

        tourTimeResponse.setRemainPax(calculateRemainPax(tourTime));
        tourTimeResponse.setTourName(tourTime.getTour().getTourName());

        ArrayList<TransportResponse> transportResponseList = new ArrayList<>();
        for (TransportDetail transportDetail : tourTime.getTransportDetails()) {
            if (status != null && transportDetail.getStatus() != status) continue;
            TransportResponse transportResponse = transportService.toTransportResponse(transportDetail, status);
            transportResponseList.add(transportResponse);
        }
        tourTimeResponse.setTransportResponses(transportResponseList);
        return tourTimeResponse;
    }

    public TourTimeResponse getTourTimeResponseById(String tourTimeId, Integer status) {
        TourTime tourTime = tourTimeRepository.findById(Integer.parseInt(tourTimeId))
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy tour time với ID: " + tourTimeId));
        if (status != null && tourTime.getStatus() == 0) return null;
        return toTourTimeResponse(tourTime, status);
    }

    public Optional<TourTime> findById(Integer id, Integer status) {
        Optional<TourTime> tourTime;
        if (status == null)
            tourTime = tourTimeRepository.findById(id);
        else
            tourTime = tourTimeRepository.findBytourTimeIdAndStatus(id, status);
        return tourTime;
    }
 // TourTimeService.java
    public TourTime addTourTime(TourTime tourTime) {
        try {
            return tourTimeRepository.save(tourTime);  // Lưu thời gian tour vào cơ sở dữ liệu
        } catch (Exception e) {
            throw new RuntimeException("Không thể thêm thời gian tour. Lỗi cơ sở dữ liệu: " + e.getMessage(), e);
        }
    }

    // Cập nhật tour time
    public boolean updateTourTime(TourTime existingTourTime) {
        try {
            // Thực hiện cập nhật thông tin vào cơ sở dữ liệu
            tourTimeRepository.save(existingTourTime); // Hoặc sử dụng phương thức phù hợp với bạn
            return true;
        } catch (Exception e) {
            // Xử lý lỗi, log, v.v.
            return false;
        }
    }


    // Xóa tour time
    public boolean deleteTourTime(int tourTimeId) {
        if (!tourTimeRepository.existsById(tourTimeId)) {
            return false;
        }
        tourTimeRepository.deleteById(tourTimeId);
        return true;
    }
 // Lấy tất cả các TourTime
    public List<TourTime> getAllTourTimes() {
        return tourTimeRepository.findAll();
    }

    // Lấy TourTime theo ID
    public Optional<TourTime> getTourTimeById(String id) {
        return tourTimeRepository.findById(Integer.parseInt(id));
    }
    // Lấy TourTime theo ID
    public TourTime getTourTimeByIdINT(int id) {
        return tourTimeRepository.findById(id).orElse(null);
    }

    public List<TourTime> getTourTimesByTourId(int tourId) {
        return tourTimeRepository.findByTourId(tourId);
    }
    public Optional<TourTime> getTourTimeByIdAdmin(String id) {
        return tourTimeRepository.findByIdAdmin(Integer.parseInt(id));
    }

    public void updateStatusToZero(Integer tourTimeId) {
        tourTimeRepository.updateStatusToZero(tourTimeId);
    }
}
