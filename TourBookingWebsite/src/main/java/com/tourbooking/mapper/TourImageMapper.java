package com.tourbooking.mapper;

import com.tourbooking.dto.response.TourImageResponse;
import com.tourbooking.model.TourImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TourImageMapper {

    TourImageResponse toTourImageResponse(TourImage tourImage);

    TourImage toTourImage(TourImageResponse tourImageResponse);
}
