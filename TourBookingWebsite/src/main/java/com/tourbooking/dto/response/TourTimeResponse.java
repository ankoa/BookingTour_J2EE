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
    Integer tourTimeId;

    // in tour
    Integer tourId;
    String dayStay;
    String tourName;

    //
    Integer discountValue;
    Boolean isDiscount;
    Integer remainPax;
    List<String> listImage;
    List<TransportResponse> transportResponses;
}
