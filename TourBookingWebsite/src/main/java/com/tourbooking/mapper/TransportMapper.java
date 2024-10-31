package com.tourbooking.mapper;

import com.tourbooking.dto.response.TransportResponse;
import com.tourbooking.model.TransportDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransportMapper {

    @Mapping(source = "transport.transportName", target = "transportName")
    @Mapping(source = "transport.departureLocation", target = "departureLocation")
    @Mapping(source = "transport.destinationLocation", target = "destinationLocation")
    @Mapping(source = "transport.transportCode", target = "transportCode")
    TransportResponse toTransportResponse(TransportDetail transportDetail);

}
