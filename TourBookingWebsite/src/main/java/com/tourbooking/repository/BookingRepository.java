package com.tourbooking.repository;

import com.tourbooking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookingRepository extends JpaRepository<Booking, Integer> {
}
