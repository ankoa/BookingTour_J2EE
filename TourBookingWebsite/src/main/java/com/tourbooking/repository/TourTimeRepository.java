package com.tourbooking.repository;

import com.tourbooking.model.TourTime;
import org.springframework.data.jpa.repository.JpaRepository;
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

}
