package com.tourbooking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingDetailResponse {

    private int price;
    private String detail;
    private CustomerResponse customerResponse;

}
