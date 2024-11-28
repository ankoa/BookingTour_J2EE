package com.tourbooking.repository;

import com.tourbooking.model.TourTime;

import jakarta.transaction.Transactional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TourTimeRepository extends JpaRepository<TourTime, Integer> {
//    @Query("SELECT tt FROM TourTime tt WHERE MONTH(tt.departureTime) = :month AND tt.tourId=:tourId")
//    List<TourTime> findByDepartureMonth(@Param("month") int mont,@Param("tourId") int TourId);
    Optional<TourTime> findBytourTimeIdAndStatus(Integer tourId, Integer status);
    @Query("SELECT t FROM TourTime t WHERE t.tour.id = :tourId")
    List<TourTime> findByTourId(@Param("tourId") int tourId);
    @Query("SELECT t FROM TourTime t WHERE t.tourTimeId = :tourTimeId")
    Optional<TourTime> findByIdAdmin(@Param("tourTimeId") Integer tourTimeId);

    @Query("SELECT ti FROM TourTime ti " +
            "WHERE ti.tour.tourId = :tourId " +
            "AND ti.status = :status " +
            "ORDER BY ti.tourTimeId ASC")
    List<TourTime> findByTour_TourIdAndStatus(int tourId, Integer status, Sort sort);
    @Modifying
    @Transactional
    @Query("UPDATE TourTime tt SET tt.status = 0 WHERE tt.tourTimeId = :tourTimeId")
    void updateStatusToZero(@Param("tourTimeId") Integer tourTimeId);

}
