package com.tourbooking.mapper;

import com.tourbooking.dto.response.TourResponse;
import com.tourbooking.model.Tour;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TourMapper {
    TourResponse toTourResponse(Tour tour);

    Tour toTour(TourResponse tourResponse);
}
