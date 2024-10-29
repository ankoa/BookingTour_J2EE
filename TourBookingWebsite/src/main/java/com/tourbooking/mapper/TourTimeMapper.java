package com.tourbooking.mapper;

import com.tourbooking.dto.response.TourTimeResponse;
import com.tourbooking.model.TourTime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TourTimeMapper {

    @Mapping(source = "tour.tourId", target = "tourId")
    @Mapping(source = "tour.dayStay", target = "dayStay")
    TourTimeResponse toTourTimeResponse(TourTime tourTime);

    TourTime toTourTime(TourTimeResponse TourTimeResponse);
}
