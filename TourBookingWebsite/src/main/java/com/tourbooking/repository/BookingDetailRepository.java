package com.tourbooking.repository;

import com.tourbooking.model.BookingDetail;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface BookingDetailRepository extends JpaRepository<BookingDetail, Integer> {
    List<BookingDetail> findByBooking_BookingId(int bookingId);

}

