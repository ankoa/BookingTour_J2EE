package com.tourbooking.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TourImageResponse {

    private Integer imageId;

    private String imageUrl;

    private int status;
}
