package com.tourbooking.service;

import com.tourbooking.dto.response.BookingDetailResponse;
import com.tourbooking.mapper.BookingDetailMapper;
import com.tourbooking.mapper.CustomerMapper;
import com.tourbooking.model.BookingDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingDetailService {

    @Autowired
    BookingDetailMapper bookingDetailMapper;

    @Autowired
    CustomerMapper customerMapper;


    public BookingDetailResponse toBookingDetailResponse(BookingDetail bookingDetail) {
        BookingDetailResponse bookingDetailResponse = bookingDetailMapper.toBookingDetailResponse(bookingDetail);

        bookingDetailResponse.setCustomerResponse(
                customerMapper
                        .toCustomerResponse(
                                bookingDetail.getCustomer()
                        )
        );
        return bookingDetailResponse;
    }


}