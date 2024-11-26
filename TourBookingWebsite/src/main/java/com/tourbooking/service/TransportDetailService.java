package com.tourbooking.service;

import com.tourbooking.model.TransportDetail;
import com.tourbooking.repository.TransportDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransportDetailService {

    @Autowired
    private TransportDetailRepository transportDetailRepository;

    // Lấy tất cả TransportDetail
    public List<TransportDetail> getAllTransportDetails() {
        return transportDetailRepository.findAll();
    }

    // Lấy TransportDetail theo ID
    public Optional<TransportDetail> getTransportDetailById(int id) {
        return transportDetailRepository.findById(id);
    }

    // Lưu TransportDetail mới hoặc cập nhật
    public TransportDetail saveTransportDetail(TransportDetail transportDetail) {
        return transportDetailRepository.save(transportDetail);
    }

    // Xóa TransportDetail theo ID
    public void deleteTransportDetail(int id) {
        transportDetailRepository.deleteById(id);
    }
}
