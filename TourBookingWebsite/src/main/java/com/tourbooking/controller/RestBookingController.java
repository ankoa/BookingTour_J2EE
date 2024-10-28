package com.tourbooking.controller;

import com.tourbooking.dto.request.BookingRequest;
import com.tourbooking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/order-booking")
public class RestBookingController {

    @Autowired
    BookingService bookingService;

    @PostMapping("/submit-form")
    public ResponseEntity<Map<String, String>> submitForm(@RequestBody BookingRequest bookingRequest) {
        bookingService.submitForm(bookingRequest);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Gửi dữ liệu thành công!");

        return ResponseEntity.ok(response);
    }
}
