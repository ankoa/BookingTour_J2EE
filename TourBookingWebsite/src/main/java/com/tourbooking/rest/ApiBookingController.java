package com.tourbooking.rest;

import com.tourbooking.service.payment.PaymentMomoService;
import com.tourbooking.service.payment.PaymentDTO;
import com.tourbooking.dto.request.BookingRequest;
import com.tourbooking.model.Booking;
import com.tourbooking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import lombok.Builder;

@Builder
@RestController
@RequestMapping("/api/order-booking")
@CrossOrigin("*")
public class ApiBookingController {

    @Autowired
    BookingService bookingService;

    @Autowired
    PaymentMomoService paymentMomoService;

    @PostMapping("/submit-form")
    public PaymentDTO.PaymentResponse submitForm( @RequestBody BookingRequest bookingRequest) {
        Booking booking = bookingService.submitForm(bookingRequest, 1);
        if (booking != null) {
            if (!bookingRequest.getPaymentMethod().equals("cash")) {
              if( bookingRequest.getPaymentMethod().equals("vn-pay")){
                PaymentDTO.PaymentResponse pay = paymentMomoService.createMomoPayment(booking.getBookingId());
                return PaymentDTO.PaymentResponse.builder()
                        .code("ok")
                        .message("success")
                        .paymentUrl(pay.paymentUrl)
                        .bookingId(booking.getBookingId()+"").build();
            }
            if( bookingRequest.getPaymentMethod().equals("momo")){
                PaymentDTO.PaymentResponse pay = paymentMomoService.createMomoPayment(booking.getBookingId());
                return PaymentDTO.PaymentResponse.builder()
                        .code("ok")
                        .message("success")
                        .paymentUrl(pay.paymentUrl)
                        .bookingId(booking.getBookingId()+"").build();
            }

            }
            return PaymentDTO.PaymentResponse.builder()
                    .code("ok")
                    .message("success")
                    .paymentUrl("")
                    .bookingId(booking.getBookingId()+"").build();
        }
        return PaymentDTO.PaymentResponse.builder()
                .code("bad")
                .message("failed")
                .paymentUrl("")
                .bookingId("").build();

    }
}
