package com.tourbooking.dto.response;

import com.tourbooking.model.Transport;
import com.tourbooking.model.TransportDetail;
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
    LocalDateTime departureTime;
    LocalDateTime arrivalTime;
    String departureLocation;
    String destinationLocation;
    String transportCode;
    String transportType;
    Boolean isOutbound;

    public TransportResponse(Transport transport, TransportDetail transportDetail) {
        this.transportName =transport.getTransportName();
        this.departureTime=transportDetail.getDepartureTime();
        this.arrivalTime = transportDetail.getArrivalTime();
        this.departureLocation = transport.getDepartureLocation();
        this.destinationLocation = transport.getDestinationLocation();
        this.transportCode = transport.getTransportCode();
        this.transportType = transport.getStatus()==1?"Bus":"Plane";
        this.isOutbound = transportDetail.getStatus() == 1;
    }
}
