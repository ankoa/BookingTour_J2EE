package com.tourbooking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransportResponse {
    String transportName;
    String transportCode;
    String departureLocation;
    String destinationLocation;

    LocalDateTime departureTime;
    LocalDateTime arrivalTime;
    Boolean isOutbound;
}
