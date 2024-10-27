package com.tourbooking.controller;

import com.tourbooking.model.Discount;
import com.tourbooking.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/discount")
public class RestDiscountController {
    @Autowired
    public DiscountService discountService;

    @GetMapping
    public ResponseEntity<Map<String, String>> getDiscountById(@RequestParam("code") String code) {
        Discount discount = discountService.getDiscountByDiscountCode(code);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Gửi dữ liệu thành công!");
        response.put("discountValue", discount.getDiscountValue()+"");

        return ResponseEntity.ok(response);
    }

}
