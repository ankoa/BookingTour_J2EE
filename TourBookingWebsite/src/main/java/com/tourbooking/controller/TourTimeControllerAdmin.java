package com.tourbooking.controller;

import com.tourbooking.model.TourTime;
import com.tourbooking.model.Tour;
import com.tourbooking.service.TourTimeService;
import com.tourbooking.service.TourService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class TourTimeControllerAdmin {
    private static final Logger logger = LoggerFactory.getLogger(TourTimeControllerAdmin.class);

    @Autowired
    private TourTimeService tourTimeService;

    @Autowired
    private TourService tourService;

    @GetMapping("/tour-times")
    public String getTourTimeList(Model model) {
        return "admin/tour-time-list";
    }

    @GetMapping("/tour-times/list")
    public ResponseEntity<List<TourTime>> getAllTourTimes() {
        List<TourTime> tourTimes = tourTimeService.getAllTourTimes();
        logger.info("Loaded tour times: {}", tourTimes);
        return ResponseEntity.ok(tourTimes);
    }

    @GetMapping("/tour-times/{id}")
    public ResponseEntity<TourTime> getTourTimeById(@PathVariable String id) {
        return tourTimeService.getTourTimeById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /*@PostMapping("/tour-times/add")
    public ResponseEntity<Map<String, String>> addTourTime(@RequestBody Map<String, Object> tourTimeData) {
        Map<String, String> response = new HashMap<>();
        try {
            String tourTimeCode = (String) tourTimeData.get("tourTimeCode");
            String timeName = (String) tourTimeData.get("timeName");
            Integer tourId = ((Number) tourTimeData.get("tour")).intValue();
            LocalDateTime departureTime = LocalDateTime.parse((String) tourTimeData.get("departureTime"));
            LocalDateTime returnTime = LocalDateTime.parse((String) tourTimeData.get("returnTime"));
            Integer quantity = ((Number) tourTimeData.get("quantity")).intValue();
            Integer priceAdult = ((Number) tourTimeData.get("priceAdult")).intValue();
            Integer priceChild = ((Number) tourTimeData.get("priceChild")).intValue();
            String note = (String) tourTimeData.get("note");
            Integer status = ((Number) tourTimeData.get("status")).intValue();

            if (tourTimeCode == null || timeName == null || tourId == null || departureTime == null ||
                    returnTime == null || quantity == null || priceAdult == null || priceChild == null || status == null) {
                response.put("message", "Vui lòng điền đầy đủ thông tin!");
                return ResponseEntity.badRequest().body(response);
            }

            Optional<Tour> optionalTour = tourService.getTourByIdInt(tourId);
            if (!optionalTour.isPresent()) {
                response.put("message", "Tour không tồn tại!");
                return ResponseEntity.badRequest().body(response);
            }

            Tour tour = optionalTour.get();
            TourTime newTourTime = new TourTime();
            newTourTime.setTourTimeCode(tourTimeCode);
            newTourTime.setTimeName(timeName);
            newTourTime.setDepartureTime(departureTime);
            newTourTime.setReturnTime(returnTime);
            newTourTime.setQuantity(quantity);
            newTourTime.setPriceAdult(priceAdult);
            newTourTime.setPriceChild(priceChild);
            newTourTime.setNote(note);
            newTourTime.setStatus(status);
            newTourTime.setTour(tour);

            tourTimeService.addTourTime(newTourTime);
            response.put("message", "Thêm tour time thành công!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Có lỗi xảy ra: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
*/
    @PutMapping("/tour-times/update")
    public ResponseEntity<Map<String, String>> updateTourTime(@RequestBody Map<String, Object> tourTimeData) {
        Map<String, String> response = new HashMap<>();
        try {
            Integer tourTimeId = ((Number) tourTimeData.get("tourTimeId")).intValue();
            String timeName = (String) tourTimeData.get("timeName");
            Integer status = ((Number) tourTimeData.get("status")).intValue();

            if (tourTimeId == null || timeName == null || status == null) {
                response.put("message", "Vui lòng điền đầy đủ thông tin!");
                return ResponseEntity.badRequest().body(response);
            }

            TourTime existingTourTime = tourTimeService.getTourTimeById(tourTimeId+"")
                    .orElseThrow(() -> new IllegalArgumentException("Tour time không tồn tại!"));

            existingTourTime.setTimeName(timeName);
            existingTourTime.setStatus(status);

            tourTimeService.updateTourTime(existingTourTime);
            response.put("message", "Cập nhật tour time thành công!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Có lỗi xảy ra: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/tour-times/delete/{id}")
    public ResponseEntity<String> deleteTourTime(@PathVariable int id) {
        boolean isDeleted = tourTimeService.deleteTourTime(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tour time không tồn tại.");
        }
    }
}
