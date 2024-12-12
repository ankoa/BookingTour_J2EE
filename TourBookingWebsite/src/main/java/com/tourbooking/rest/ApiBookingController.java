package com.tourbooking.rest;

import com.tourbooking.dto.response.BookingResponse;
import com.tourbooking.security.CustomUserDetails;
import com.tourbooking.service.BookingService;
import com.tourbooking.service.payment.PaymentBookingService;
import com.tourbooking.dto.response.PaymentDTO;
import com.tourbooking.dto.request.BookingRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import lombok.Builder;

import java.util.List;

@Builder
@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class ApiBookingController {

    @Autowired
    PaymentBookingService paymentBookingService;

    @Autowired
    BookingService bookingService;

    @PostMapping("/order-booking/submit-form")
    public PaymentDTO.PaymentResponse submitFormBooking(@RequestBody BookingRequest bookingRequest, HttpServletRequest request) {
        return paymentBookingService.submitFormBooking(bookingRequest, request, 1);
    }

    @GetMapping("/account-info")
    public List<BookingResponse> getBooking(@RequestParam int page,
                                            @AuthenticationPrincipal CustomUserDetails user) {
        if(user==null)return null;
        return bookingService.getBookingResponses(user.getAccount(), null, page, 5);
    }
}
