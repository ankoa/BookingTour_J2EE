package com.tourbooking.controller;

import com.tourbooking.model.TourDiscount;
import com.tourbooking.service.TourDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/admin/tour-discounts")
public class TourDiscountController {

    private final TourDiscountService tourDiscountService;

    @Autowired
    public TourDiscountController(TourDiscountService tourDiscountService) {
        this.tourDiscountService = tourDiscountService;
    }

    // Lấy tất cả mã giảm giá
    @GetMapping
    public ResponseEntity<List<TourDiscount>> getAllTourDiscounts() {
        List<TourDiscount> discounts = tourDiscountService.getAllTourDiscounts();
        return new ResponseEntity<>(discounts, HttpStatus.OK);
    }

    // Lấy mã giảm giá theo tourTimeId và discountId
    @GetMapping("/{tourTimeId}/{discountId}")
    public ResponseEntity<TourDiscount> getTourDiscountById(@PathVariable int tourTimeId, @PathVariable int discountId) {
        Optional<TourDiscount> tourDiscount = tourDiscountService.getTourDiscountById(tourTimeId, discountId);
        return tourDiscount.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addTourDiscount(@RequestBody Map<String, Object> data) {
        Map<String, Object> response = new HashMap<>();
        try {
            int discountId = Integer.parseInt(data.get("discountId").toString());
            int tourTimeId = Integer.parseInt(data.get("tourTimeId").toString());

            TourDiscount tourDiscount = new TourDiscount();
            tourDiscount.setDiscountId(discountId);
            tourDiscount.setTourTimeId(tourTimeId);

            TourDiscount createdTourDiscount = tourDiscountService.addTourDiscount(tourDiscount);

            // Trả về success = true nếu thêm thành công
            response.put("success", true);
            response.put("data", createdTourDiscount); // Dữ liệu đã tạo
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            // Trả về success = false nếu có lỗi
            response.put("success", false);
            response.put("message", "Có lỗi xảy ra: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }




    // Cập nhật mã giảm giá
    @PutMapping
    public ResponseEntity<TourDiscount> updateTourDiscount(@RequestBody TourDiscount tourDiscount) {
        TourDiscount updatedTourDiscount = tourDiscountService.updateTourDiscount(tourDiscount);
        return new ResponseEntity<>(updatedTourDiscount, HttpStatus.OK);
    }

    // Xóa mã giảm giá
    @DeleteMapping("/deleteTuorDiscount/{tourTimeId}/{discountId}")
    public ResponseEntity<Void> deleteTourDiscount(@PathVariable int tourTimeId, @PathVariable int discountId) {
        tourDiscountService.deleteTourDiscount(tourTimeId, discountId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/by-tour-time/{tourTimeId}")
    public ResponseEntity<List<TourDiscount>> getDiscountsByTourTimeId(@PathVariable int tourTimeId) {
        List<TourDiscount> discounts = tourDiscountService.getTourDiscountsByTourTimeId(tourTimeId);
        return new ResponseEntity<>(discounts, HttpStatus.OK);
    }
}
