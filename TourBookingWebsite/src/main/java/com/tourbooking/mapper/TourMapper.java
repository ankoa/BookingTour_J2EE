package com.tourbooking.mapper;

import com.tourbooking.dto.response.FindTourResponse;
import com.tourbooking.dto.response.TourResponse;
import com.tourbooking.dto.response.TourTimeResponse;
import com.tourbooking.model.Tour;
import com.tourbooking.model.TourTime;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TourMapper {
    TourResponse toTourResponse(Tour tour);

    Tour toTour(TourResponse tourResponse);
}
