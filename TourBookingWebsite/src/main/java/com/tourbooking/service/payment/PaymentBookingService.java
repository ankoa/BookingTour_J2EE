package com.tourbooking.service.payment;

import com.tourbooking.dto.request.BookingRequest;
import com.tourbooking.dto.response.ResponseObject;
import com.tourbooking.model.*;
import com.tourbooking.service.BookingService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PaymentBookingService {
    @Autowired
    BookingService bookingService;

    @Autowired
    PaymentVNPayService paymentVNPayService;

    @Autowired
    PaymentMomoService paymentMomoService;

    public PaymentDTO.PaymentResponse submitFormBooking(BookingRequest bookingRequest,
                                                        HttpServletRequest request,
                                                        Integer status) {
        ResponseObject<Booking> responseBooking=bookingService.createBooking(bookingRequest,status);

        Booking booking =Objects.requireNonNull(responseBooking.getBody()).data;
        if (booking != null) {
            if (!bookingRequest.getPaymentMethod().equals("cash")) {
                if( bookingRequest.getPaymentMethod().equals("vn-pay")){
                    PaymentDTO.PaymentResponse pay = paymentVNPayService.createVnPayPayment(request,booking );
                    return PaymentDTO.PaymentResponse.builder()
                            .code("ok")
                            .message("success")
                            .paymentUrl(pay.paymentUrl)
                            .bookingId(booking.getBookingId()+"").build();
                }
                if( bookingRequest.getPaymentMethod().equals("momo")){
                    PaymentDTO.PaymentResponse pay = paymentMomoService.createMomoPayment(booking);
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
                .message(responseBooking.getBody().message)
                .paymentUrl("")
                .bookingId("").build();

    }


}
