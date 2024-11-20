
package com.tourbooking.mapper;

import com.tourbooking.dto.response.BookingDetailResponse;
import com.tourbooking.model.BookingDetail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookingDetailMapper {
    BookingDetailResponse toBookingDetailResponse(BookingDetail bookingDetail);

    BookingDetail toBookingDetail(BookingDetailResponse bookingDetailResponse);

}
