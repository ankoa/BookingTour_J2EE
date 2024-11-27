package com.tourbooking.service.payment;

import com.tourbooking.dto.response.ResponseObject;
import com.tourbooking.service.payment.PaymentDTO;
import com.tourbooking.service.payment.PaymentVNPayService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("${spring.application.api-prefix}/payment")
@RequiredArgsConstructor
public class PaymentVNPayController {
    private final PaymentVNPayService paymentService;

    // chua dung
    @GetMapping("/vn-pay")
    public ResponseObject<PaymentDTO.PaymentResponse> pay(HttpServletRequest request,
                                                             @RequestParam String orderInfo) {
        PaymentDTO.PaymentResponse pay =paymentService.createVnPayPayment(request,orderInfo);
        if(pay==null) return new ResponseObject<>(HttpStatus.NOT_FOUND, "Fail",null);
        return new ResponseObject<>(HttpStatus.OK, "Success",pay );
    }

    @GetMapping("/callback/vn-pay")
    public void payCallbackHandler(HttpServletRequest request,
                                   HttpServletResponse response) throws IOException {
        String status = request.getParameter("vnp_ResponseCode");
        String bookingId = request.getParameter("vnp_OrderInfo");
        if ("00".equals(status)) {
            if ( paymentService.OrderSuccess(bookingId))
                response.sendRedirect("/booking/"+bookingId);
        } else {
            // Chuyển hướng đến trang thất bại
            response.sendRedirect("/payment-failure");
        }
    }
}
