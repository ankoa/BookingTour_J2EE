package com.tourbooking.repository;

import com.tourbooking.model.TourTime;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TourTimeRepository extends JpaRepository<TourTime, Integer> {
//    @Query("SELECT tt FROM TourTime tt WHERE MONTH(tt.departureTime) = :month AND tt.tourId=:tourId")
//    List<TourTime> findByDepartureMonth(@Param("month") int mont,@Param("tourId") int TourId);
    Optional<TourTime> findBytourTimeIdAndStatus(Integer id, Integer status);
}
