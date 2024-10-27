package com.tourbooking.repository;

import com.tourbooking.model.BookingDetail;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookingDetailRepository extends JpaRepository<BookingDetail, Integer> {
}
