package com.tourbooking.repository;

import com.tourbooking.model.Tour;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TourRepository extends JpaRepository<Tour, Integer> {

}
