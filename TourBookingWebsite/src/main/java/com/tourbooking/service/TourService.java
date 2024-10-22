package com.tourbooking.service;

import com.tourbooking.model.Tour;
import com.tourbooking.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TourService {

    @Autowired
    private TourRepository tourRepository;


    public List<Tour> getAllTours() {
        return tourRepository.findAll();
    }

    public Tour getTourById(String id) {

        return tourRepository.findById(Integer.parseInt(id)).orElse(null);
    }
}
