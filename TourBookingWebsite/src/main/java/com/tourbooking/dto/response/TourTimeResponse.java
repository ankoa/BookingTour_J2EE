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
public class TourTimeResponse {
    String tourTimeCode;
    String timeName;
    LocalDateTime departureTime;
    LocalDateTime returnTime;
    Integer quantity;
    Integer priceAdult;
    Integer priceChild;
    String note;

    // in tour
    Integer tourId;
    String dayStay;
    Integer tourTimeId;

    //
    Integer discountPrice;
    Boolean isDiscount;
    Integer remainPax;
    List<TransportResponse> transportResponses;
}
