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
    public ResponseEntity<Map<String, Integer>> submitForm(@RequestBody BookingRequest bookingRequest) {
        Map<String, Integer> response = new HashMap<>();
        if (bookingService.submitForm(bookingRequest)) {
            response.put("status", 200);
            return ResponseEntity.ok(response);  // Success
        } else {
            response.put("status", 400);
            return ResponseEntity.status(400).body(response);  // Error
        }
    }
}
