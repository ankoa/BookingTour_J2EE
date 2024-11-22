
package com.tourbooking.mapper;

import com.tourbooking.dto.request.BookingRequest;
import com.tourbooking.dto.response.BookingResponse;
import com.tourbooking.model.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    BookingRequest toBookingRequest(Booking booking);

    Booking toBooking(BookingRequest BookingRequest);

    @Mapping(target = "customerRelated", source = "customer")
    BookingResponse toBookingResponse(Booking booking);
}
