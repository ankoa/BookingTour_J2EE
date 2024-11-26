package com.tourbooking.controller;

import com.tourbooking.model.TourTime;
import com.tourbooking.model.Transport;
import com.tourbooking.model.TransportDetail;
import com.tourbooking.service.TourTimeService;
import com.tourbooking.service.TransportDetailService;
import com.tourbooking.service.TransportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/admin/transport-details")
public class TransportDetailController {

    @Autowired
    private TransportDetailService transportDetailService;
    @Autowired
    private TourTimeService tourTimeService;
    @Autowired
    private TransportService transportService;
    // Lấy tất cả TransportDetails
    @GetMapping
    public List<TransportDetail> getAllTransportDetails() {
        return transportDetailService.getAllTransportDetails();
    }

    // Lấy TransportDetail theo ID
    @GetMapping("/{id}")
    public ResponseEntity<TransportDetail> getTransportDetailById(@PathVariable int id) {
        Optional<TransportDetail> transportDetail = transportDetailService.getTransportDetailById(id);
        if (transportDetail.isPresent()) {
            return ResponseEntity.ok(transportDetail.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @PostMapping("/addTransportToTourTime")
    public ResponseEntity<TransportDetail> createOrUpdateTransportDetail(@RequestBody Map<String, Object> data) {
        try {
            // Kiểm tra và chuyển đổi transportId
            Integer transportId = null;
            if (data.get("transportId") instanceof Integer) {
                transportId = (Integer) data.get("transportId");
            } else if (data.get("transportId") instanceof String) {
                transportId = Integer.parseInt((String) data.get("transportId"));
            }

            // Kiểm tra và chuyển đổi tourTimeId
            Integer tourTimeId = null;
            if (data.get("tourTimeId") instanceof Integer) {
                tourTimeId = (Integer) data.get("tourTimeId");
            } else if (data.get("tourTimeId") instanceof String) {
                tourTimeId = Integer.parseInt((String) data.get("tourTimeId"));
            }

            // Lấy các trường khác
            String arrivalTimeStr = (String) data.get("arrivalTime");
            String departureTimeStr = (String) data.get("departureTime");
            Integer status = null;
            if (data.get("status") instanceof Integer) {
                status = (Integer) data.get("status");
            } else if (data.get("status") instanceof String) {
                status = Integer.parseInt((String) data.get("status"));
            }

            // Kiểm tra dữ liệu bắt buộc
            if (transportId == null || tourTimeId == null || arrivalTimeStr == null || departureTimeStr == null || status == null) {
                return ResponseEntity.badRequest().body(null); // Trả về lỗi 400 nếu dữ liệu thiếu
            }

            // Kiểm tra tồn tại của Transport và TourTime
            if (transportService.getTransportById(transportId) == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Transport không tồn tại
            }
            if (tourTimeService.getTourTimeByIdINT(tourTimeId) == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // TourTime không tồn tại
            }

            // Chuyển đổi thời gian
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime arrivalTime = LocalDateTime.parse(arrivalTimeStr, formatter);
            LocalDateTime departureTime = LocalDateTime.parse(departureTimeStr, formatter);

            // Tạo đối tượng TransportDetail
            TransportDetail transportDetail = new TransportDetail();
            transportDetail.setTransport(transportService.getTransportById(transportId));
            transportDetail.setTourTime(tourTimeService.getTourTimeByIdINT(tourTimeId));
            transportDetail.setArrivalTime(arrivalTime);
            transportDetail.setDepartureTime(departureTime);
            transportDetail.setStatus(status);

            // Lưu hoặc cập nhật
            TransportDetail savedTransportDetail = transportDetailService.saveTransportDetail(transportDetail);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedTransportDetail);

        } catch (NumberFormatException e) {
            // Xử lý lỗi chuyển đổi chuỗi thành số
            System.out.println("NumberFormatException: " + e.getMessage());
            return ResponseEntity.badRequest().body(null);
        } catch (DateTimeParseException e) {
            // Xử lý lỗi chuyển đổi thời gian
            System.out.println("DateTimeParseException: " + e.getMessage());
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            // Xử lý lỗi chung
            System.out.println("General Error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }












    // Xóa TransportDetail theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTransportDetail(@PathVariable int id) {
        transportDetailService.deleteTransportDetail(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("TransportDetail with ID " + id + " deleted successfully");
    }
}
