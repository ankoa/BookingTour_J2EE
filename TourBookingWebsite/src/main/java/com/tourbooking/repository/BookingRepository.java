package com.tourbooking.repository;

import com.tourbooking.model.Booking;
import com.tourbooking.model.TourTime;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    // Lấy tất cả các TourTime liên quan đến Booking
    @Query("SELECT b.tourTime FROM Booking b")
    List<TourTime> findAllTourTimes();

    @Modifying
    @Transactional
    @Query("UPDATE Booking b SET b.status = 0 WHERE b.bookingId = :bookingId")
    int deactivateBooking(@Param("bookingId") Integer id);


}
