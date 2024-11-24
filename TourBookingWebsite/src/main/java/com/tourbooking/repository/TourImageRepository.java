package com.tourbooking.repository;

import com.tourbooking.model.TourImage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourImageRepository extends JpaRepository<TourImage, Integer> {

    @Query("SELECT ti FROM TourImage ti WHERE ti.tour.tourId = :tourId")
    List<TourImage> findByTourId(@Param("tourId") int tourId);
}
