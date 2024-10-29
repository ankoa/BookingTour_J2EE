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

    public int getReservedCount(TourTime tourTime) {
        int reservedCount=tourTime.getQuantity();
        Set<Booking> bookings =tourTime.getBookings();
        for(Booking booking : bookings){
            reservedCount+=booking.getAdultCount()+booking.getChildCount();
        }
        return reservedCount;
    }

    public String getTourName(String id) {
        TourTime tourTime= tourTimeRepository.findById(Integer.parseInt(id))
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy tour time với ID: " + id));
        return tourTime.getTour().getTourName();
    }
    public List<TourTimeResponse> getListTourTimeResponseByTourId(String tourId) {
        Tour tour = tourRepository.findById(Integer.parseInt(tourId))
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy tour với ID: " + tourId));

        Set<TourTime> tourTimes = tour.getTourTimes();

        List<TourTimeResponse> monthMap = new ArrayList<>();
        for (TourTime tourTime : tourTimes) {
            int reservedCount = getReservedCount(tourTime);
            TourTimeResponse tourTimeResponse = toTourTimeResponse(tourTime, (tourTime.getQuantity() - reservedCount));
            monthMap.add(tourTimeResponse);
        }
        monthMap.sort((t1, t2) -> t1.getDepartureTime().compareTo(t2.getDepartureTime()));

        return monthMap;
    }

    private TourTimeResponse toTourTimeResponse(TourTime tourTime, int tourReservedCount) {
        TourTimeResponse tourTimeResponse = tourTimeMapper.toTourTimeResponse(tourTime);

        Date currentDate = new Date();

        Set<Discount> discounts = tourTime.getDiscounts();
        if (!discounts.isEmpty())
            for (Discount discount : discounts) {
                if (discount.getStartDate() != null)
                    if (!currentDate.after(discount.getStartDate())) continue;
                if (discount.getEndDate() != null)
                    if (!currentDate.before(discount.getEndDate())) continue;
                tourTimeResponse.setIsDiscount(true);
                tourTimeResponse.setDiscountPrice(discount.getDiscountValue());
                break;
            }
        else {
            tourTimeResponse.setIsDiscount(false);
            tourTimeResponse.setDiscountPrice(0);
        }

        tourTimeResponse.setRemainPax(tourTime.getQuantity() - tourReservedCount);

        Set<TransportDetail> transportDetailsSet = tourTime.getTransportDetails();
        ArrayList<TransportResponse> transportResponseList = new ArrayList<>();
        transportDetailsSet.forEach(transportDetail ->
        {
            TransportResponse transportResponse = transportMapper.toTransportResponse(transportDetail);
            transportResponse.setIsOutbound(transportDetail.getStatus() == 1);
            transportResponseList.add(transportResponse);
        });
        tourTimeResponse.setTransportResponses(transportResponseList);
        return tourTimeResponse;
    }

    public TourTimeResponse getTourTimeResponseById(String tourTimeId) {
        TourTime tourTime = tourTimeRepository.findById(Integer.parseInt(tourTimeId))
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy tour time với ID: " + tourTimeId));
        int reserverCount = getReservedCount(tourTime);
        TourTimeResponse tourTimeResponse = toTourTimeResponse(tourTime, (tourTime.getQuantity() - reserverCount));
        return tourTimeResponse;
    }
}
