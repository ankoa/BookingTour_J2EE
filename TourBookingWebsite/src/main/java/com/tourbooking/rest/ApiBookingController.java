package com.tourbooking.rest;

import com.tourbooking.service.payment.PaymentBookingService;
import com.tourbooking.service.payment.PaymentDTO;
import com.tourbooking.dto.request.BookingRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import lombok.Builder;

@Builder
@RestController
@RequestMapping("/api/order-booking")
@CrossOrigin("*")
public class ApiBookingController {

    @Autowired
    PaymentBookingService paymentBookingService;

    @PostMapping("/submit-form")
    public PaymentDTO.PaymentResponse submitFormBooking( @RequestBody BookingRequest bookingRequest, HttpServletRequest request) {
        return paymentBookingService.submitFormBooking(bookingRequest,request,1);

    }
}
