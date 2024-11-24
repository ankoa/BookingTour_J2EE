package com.tourbooking.service;

import com.tourbooking.dto.response.TransportResponse;
import com.tourbooking.mapper.TransportMapper;
import com.tourbooking.model.Transport;
import com.tourbooking.model.TransportDetail;
import com.tourbooking.repository.TransportRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransportService {
    @Autowired
    TransportMapper transportMapper;
    @Autowired
    private TransportRepository transportRepository;

    public TransportResponse toTransportResponse(TransportDetail transportDetail,Integer status){
        if(status!=null && transportDetail.getStatus()!=status) return null;
        TransportResponse transportResponse = transportMapper.toTransportResponse(transportDetail);
        transportResponse.setIsOutbound(transportDetail.getStatus() == 1);
        return transportResponse;
    }
    // Lấy tất cả phương tiện
    public List<Transport> getAllTransports() {
        return transportRepository.findAll();
    }

    // Tìm phương tiện theo ID
    public Transport getTransportById(int transportId) {
        return transportRepository.findById(transportId).orElse(null);
    }

    // Cập nhật phương tiện
 // Cập nhật phương tiện
//    public Transport updateTransport(int transportId, Transport transportDetails) {
//        Transport transport = transportRepository.findById(transportId).orElse(null);
//        if (transport != null) {
//            // Cập nhật các trường hiện có trong Transport
//            transport.setTransportName(transportDetails.getTransportName());
//            transport.setTransportCode(transportDetails.getTransportCode());
//            transport.setDepartureLocation(transportDetails.getDepartureLocation());
//            transport.setDestinationLocation(transportDetails.getDestinationLocation());
//            transport.setStatus(transportDetails.getStatus());
//            
//            // Lưu lại thay đổi
//            return transportRepository.save(transport);
//        }
//        return null;
//    }
    public boolean updateTransport(Transport transportDetails) {
        Transport transport = transportRepository.findById(transportDetails.getTransportId()).orElse(null);
        if (transport != null) {
            // Cập nhật các trường hiện có trong Transport
            transport.setTransportName(transportDetails.getTransportName());
            transport.setTransportCode(transportDetails.getTransportCode());
            transport.setDepartureLocation(transportDetails.getDepartureLocation());
            transport.setDestinationLocation(transportDetails.getDestinationLocation());
            transport.setStatus(transportDetails.getStatus());
            transportRepository.save(transport);
            // Lưu lại thay đổi
            return true;
        }
        return false;
    }


    public boolean deleteTransport(String transportId) {
        try {
            transportRepository.deactivateTransport(Integer.parseInt(transportId));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
 // Thêm phương tiện mới
    public Transport addTransport(Transport transport) {
        return transportRepository.save(transport);
    }
}
