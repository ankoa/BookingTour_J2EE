package com.tourbooking.controller.client;

import com.tourbooking.dto.response.ResponseObject;
import com.tourbooking.dto.response.PaymentDTO;
import com.tourbooking.service.payment.PaymentVNPayService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("${spring.application.api-prefix}/payment/vn-pay")
@RequiredArgsConstructor
public class PaymentVNPayController {
    private final PaymentVNPayService paymentVNPayService;

    @GetMapping
    public ResponseObject<PaymentDTO.PaymentResponse> pay(HttpServletRequest request,
                                                          @RequestParam String bookingId) {
        PaymentDTO.PaymentResponse pay =paymentVNPayService.createVnPayPayment(request,bookingId);
        if(pay==null) return new ResponseObject<>(HttpStatus.NOT_FOUND, "Fail",null);
        return new ResponseObject<>(HttpStatus.OK, "Success",pay );
    }

    @GetMapping("/amount")
    public ResponseObject<PaymentDTO.PaymentResponse> payWithAmount(HttpServletRequest request,
                                                                    @RequestParam String amount,
                                                                    @RequestParam String orderId,
                                                                    @RequestParam String returnUrl) {
        if(amount==null||orderId==null||returnUrl==null) return new ResponseObject<>(HttpStatus.BAD_REQUEST, "Fail",null);
        PaymentDTO.PaymentResponse pay =paymentVNPayService.createVnPayPaymentWithAmount(request,amount,orderId,returnUrl);
        if(pay==null) return new ResponseObject<>(HttpStatus.NOT_FOUND, "Fail",null);
        return new ResponseObject<>(HttpStatus.OK, "Success",pay );
    }

    @GetMapping("/callback")
    public void payCallbackHandler(HttpServletRequest request,
                                   HttpServletResponse response) throws IOException {
        String status = request.getParameter("vnp_ResponseCode");
        String bookingId = request.getParameter("vnp_OrderInfo");
        if ("00".equals(status)) {
            if ( paymentVNPayService.OrderSuccess(bookingId))
                response.sendRedirect("/booking/"+bookingId);
        } else {
            // Chuyển hướng đến trang thất bại
            response.sendRedirect("/payment-failure");
        }
    }
}
