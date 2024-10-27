package com.tourbooking.service;

import com.tourbooking.dto.response.TourTimeResponse;
import com.tourbooking.model.Booking;
import com.tourbooking.model.TourTime;
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

    public TourTimeResponse getTourTimeResponseById(String id) {
        TourTime tourTime= tourTimeRepository.findById(Integer.parseInt(id)).orElse(null);
        int reserverdCount=getReservedCount(tourTime);
        TourTimeResponse tourTimeResponse = new TourTimeResponse(tourTime,reserverdCount);
        return tourTimeResponse;
    }


    public int getReservedCount(TourTime tourTime) {
        int reservedCount=tourTime.getQuantity();
        Set<Booking> bookings =tourTime.getBookings();
        for(Booking booking : bookings){
            reservedCount+=booking.getAdultCount()+booking.getChildCount();
        }
        return reservedCount;
    }

    public String getTourName(String id) {
        TourTime tourTime= tourTimeRepository.findById(Integer.parseInt(id)).orElse(null);
        return tourTime.getTour().getTourName();
    }
}
