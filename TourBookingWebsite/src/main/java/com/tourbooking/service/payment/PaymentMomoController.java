package com.tourbooking.service.payment;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("${spring.application.api-prefix}/payment")
@RequiredArgsConstructor
public class PaymentMomoController {
    private final PaymentMomoService paymentMomoService;

    @GetMapping("/callback/momo")
    public void payMomoCallbackHandler(HttpServletRequest request,
                                   HttpServletResponse response) throws IOException {
        String status = request.getParameter("resultCode");
        String bookingId = request.getParameter("orderId");
        if ("0".equals(status)) {
            if (paymentMomoService.OrderSuccess(bookingId))
                response.sendRedirect("/booking/" + bookingId);
        } else {
            // Chuyển hướng đến trang thất bại
            response.sendRedirect("/payment-failure");
        }
    }
}
