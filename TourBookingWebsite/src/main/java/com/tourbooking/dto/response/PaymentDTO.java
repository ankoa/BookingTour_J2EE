package com.tourbooking.dto.response;

import lombok.Builder;

public abstract class PaymentDTO {
    @Builder
    public static class PaymentResponse {
        public String code;
        public String message;
        public String paymentUrl;
        public String bookingId;
    }
}
