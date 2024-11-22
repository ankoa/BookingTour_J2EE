package com.tourbooking.rest;

import com.tourbooking.VNPay.vnpay.PaymentDTO;
import com.tourbooking.VNPay.vnpay.PaymentService;
import com.tourbooking.dto.request.BookingRequest;
import com.tourbooking.model.Booking;
import com.tourbooking.service.BookingService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import lombok.Builder;

@Builder
@RestController
@RequestMapping("/api/order-booking")
@CrossOrigin("*")
public class ApiBookingController {

    @Autowired
    BookingService bookingService;

    @Autowired
    PaymentService paymentService;

    @PostMapping("/submit-form")
    public PaymentDTO.VNPayResponse submitForm(HttpServletRequest request, @RequestBody BookingRequest bookingRequest) {
        Booking booking = bookingService.submitForm(bookingRequest, 1);
        if (booking != null) {
            if (Objects.equals(bookingRequest.getPaymentMethod(), "vn-pay")) {
                PaymentDTO.VNPayResponse pay = paymentService.createVnPayPayment(request, booking.getBookingId());
                return PaymentDTO.VNPayResponse.builder()
                        .code("ok")
                        .message("success")
                        .paymentUrl(pay.paymentUrl)
                        .bookingId(booking.getBookingId()+"").build();
            }
            return PaymentDTO.VNPayResponse.builder()
                    .code("ok")
                    .message("success")
                    .paymentUrl("")
                    .bookingId(booking.getBookingId()+"").build();
        }
        return PaymentDTO.VNPayResponse.builder()
                .code("bad")
                .message("failed")
                .paymentUrl("")
                .bookingId("").build();

    }
}
