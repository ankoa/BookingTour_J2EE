package com.tourbooking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tourbooking.model.Transport;
import com.tourbooking.repository.TransportRepository;

import java.util.List;

@Service
public class TransportService {

    @Autowired
    private TransportRepository transportRepository;

    // Lấy tất cả phương tiện
    public List<Transport> getAllTransports() {
        return transportRepository.findAll();
    }

    // Tìm phương tiện theo ID
    public Transport getTransportById(int transportId) {
        return transportRepository.findById(transportId).orElse(null);
    }

    // Cập nhật phương tiện
    public Transport updateTransport(int transportId, Transport transportDetails) {
        Transport transport = transportRepository.findById(transportId).orElse(null);
        if (transport != null) {
            transport.setTransportName(transportDetails.getTransportName());
            transport.setTransportDetail(transportDetails.getTransportDetail());
            transport.setDepartureTime(transportDetails.getDepartureTime());
            transport.setDepartureLocation(transportDetails.getDepartureLocation());
            transport.setDestinationLocation(transportDetails.getDestinationLocation());
            transport.setStatus(transportDetails.getStatus());
            return transportRepository.save(transport);
        }
        return null;
    }

    // Xóa phương tiện
    public void deleteTransport(int transportId) {
        transportRepository.deleteById(transportId);
    }
 // Thêm phương tiện mới
    public Transport addTransport(Transport transport) {
        return transportRepository.save(transport);
    }
}
