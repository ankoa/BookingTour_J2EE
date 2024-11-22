package com.tourbooking.VNPay.vnpay;


import com.tourbooking.VNPay.core.config.payment.VNPAYConfig;
import com.tourbooking.VNPay.util.VNPayUtil;
import com.tourbooking.model.Booking;
import com.tourbooking.service.BookingService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentService {

    @Autowired
    BookingService bookingService;
    private final VNPAYConfig vnPayConfig;

    public PaymentDTO.VNPayResponse createVnPayPayment(HttpServletRequest request, int orderInfo) {
        Booking booking = bookingService.findById(orderInfo);
        if(booking==null) return null;
        long amount = (booking.getTotalPrice()-booking.getTotalDiscount()) * 100L;
        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_OrderInfo", orderInfo+"");
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));
        //build query url
        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;
        return PaymentDTO.VNPayResponse.builder()
                .code("ok")
                .message("success")
                .paymentUrl(paymentUrl).build();
    }

    public boolean OrderSuccess(String orderId) {
        return bookingService.orderSuccess(orderId);
    }


}
