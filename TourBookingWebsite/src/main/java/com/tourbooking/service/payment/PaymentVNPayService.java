package com.tourbooking.service.payment;


import com.tourbooking.config.payment.VNPAYConfig;
import com.tourbooking.utils.PaymentUtils;
import com.tourbooking.model.Booking;
import com.tourbooking.service.BookingService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentVNPayService {

    @Autowired
    BookingService bookingService;

    private final VNPAYConfig vnPayConfig;

    public PaymentDTO.PaymentResponse createVnPayPayment(HttpServletRequest request, int orderInfo) {
        Booking booking = bookingService.findById(orderInfo);
        if(booking==null) 
            return PaymentDTO.PaymentResponse.builder()
                .code("error")
                .message("Booking not found")
                .build();
        long amount = (booking.getTotalPrice()-booking.getTotalDiscount()) * 100L;
        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_OrderInfo", orderInfo+"");
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        vnpParamsMap.put("vnp_IpAddr", PaymentUtils.getIpAddress(request));
        //build query url
        String queryUrl = PaymentUtils.getPaymentURL(vnpParamsMap, true);
        String hashData = PaymentUtils.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = PaymentUtils.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;
        return PaymentDTO.PaymentResponse.builder()
                .code("ok")
                .message("success")
                .paymentUrl(paymentUrl).build();
    }

    public boolean OrderSuccess(String orderId) {
        return bookingService.orderSuccess(orderId);
    }


}
