package com.tourbooking.service;

import com.tourbooking.dto.response.TransportResponse;
import com.tourbooking.mapper.TransportMapper;
import com.tourbooking.model.TransportDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransportService {
    @Autowired
    TransportMapper transportMapper;

    public TransportResponse toTransportResponse(TransportDetail transportDetail,Integer status){
        if(status!=null && transportDetail.getStatus()!=status) return null;
        TransportResponse transportResponse = transportMapper.toTransportResponse(transportDetail);
        transportResponse.setIsOutbound(transportDetail.getStatus() == 1);
        return transportResponse;
    }
}
