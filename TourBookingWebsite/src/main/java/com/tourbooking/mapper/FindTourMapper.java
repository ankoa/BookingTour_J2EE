package com.tourbooking.mapper;

import com.tourbooking.dto.response.FindTourResponse;
import com.tourbooking.model.Tour;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FindTourMapper {
    FindTourResponse toFindTourResponse(Tour tour);
}
