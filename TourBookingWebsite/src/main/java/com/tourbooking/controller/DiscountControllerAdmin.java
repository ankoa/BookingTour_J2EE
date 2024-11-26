package com.tourbooking.controller;

import com.tourbooking.model.Discount;
import com.tourbooking.service.DiscountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class DiscountControllerAdmin {

    private static final Logger logger = LoggerFactory.getLogger(DiscountControllerAdmin.class);
    private final DiscountService discountService;

    @Autowired
    public DiscountControllerAdmin(DiscountService discountService) {
        this.discountService = discountService;
    }

    // Lấy tất cả mã giảm giá và hiển thị
    @GetMapping("/discounts")
    public String getAllDiscounts(Model model) {
        List<Discount> discounts = discountService.getAllDiscounts();
        model.addAttribute("discounts", discounts);
        return "admin/discount-all";
    }

    // Lấy tất cả mã giảm giá dưới dạng JSON
    @GetMapping("/discounts/listDiscount")
    @ResponseBody
    public ResponseEntity<List<Discount>> getAllDiscountsJson() {
        List<Discount> discounts = discountService.getAllDiscounts();
        if (discounts.isEmpty()) {
            logger.warn("No discounts found.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(discounts);
        } else {
            logger.info("Total discounts: {}", discounts.size());
            return ResponseEntity.ok(discounts);
        }
    }

    // Lấy mã giảm giá theo ID
    @GetMapping("/discounts/{id}")
    @ResponseBody
    public Discount getDiscountById(@PathVariable("id") int id) {
        return discountService.getDiscountById(id);
    }

    // Thêm mã giảm giá mới
    @PostMapping("/discounts/add")
    @ResponseBody
    public ResponseEntity<String> addDiscount(@RequestBody Discount discount) {
        discountService.addDiscount(discount);
        return ResponseEntity.ok("Discount added successfully");
    }

    // Cập nhật thông tin mã giảm giá
    @PutMapping("/discounts/update/{id}")
    @ResponseBody
    public ResponseEntity<String> updateDiscount(@PathVariable("id") int id, @RequestBody Discount discount) {
        boolean updatedDiscount = discountService.updateDiscount(discount);
        if (updatedDiscount) {
            return ResponseEntity.ok("Discount updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Discount not found");
        }
    }

    // Xóa mã giảm giá
    @DeleteMapping("/discounts/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> deactivateDiscount(@PathVariable String id) {
        boolean deactivated = discountService.deleteDiscount(id);
        if (deactivated) {
            return ResponseEntity.ok("Discount deactivated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to deactivate discount.");
        }
    }
}
