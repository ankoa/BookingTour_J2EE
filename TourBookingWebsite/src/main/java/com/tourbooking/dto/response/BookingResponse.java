package com.tourbooking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {

    // Booking
    private int bookingId;
    private int totalPrice;
    private int adultCount;
    private int childCount;
    private LocalDateTime time;
    private int totalDiscount;
    private String paymentMethod;
    private int status;


    private TourTimeResponse tourTimeResponse;

    private List<BookingDetailResponse> bookingDetailResponses;

    private CustomerResponse customerRelated;

}
