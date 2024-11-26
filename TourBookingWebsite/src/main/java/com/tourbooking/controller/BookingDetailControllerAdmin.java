package com.tourbooking.controller;

import com.tourbooking.model.BookingDetail;
import com.tourbooking.service.BookingDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/booking-details")
public class BookingDetailControllerAdmin {

    @Autowired
    private BookingDetailService bookingDetailService;


    // Lấy danh sách BookingDetail theo bookingId
    @GetMapping("/by-booking/{bookingId}")
    public ResponseEntity<List<BookingDetail>> getBookingDetailsByBookingId(@PathVariable int bookingId) {
        List<BookingDetail> bookingDetails = bookingDetailService.getBookingDetailsByBookingId(bookingId);
        return ResponseEntity.ok(bookingDetails);
    }

    // Các API khác (getById, create, update, delete)
}
