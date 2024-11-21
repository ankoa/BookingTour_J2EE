package com.tourbooking.rest;

import com.tourbooking.model.Discount;
import com.tourbooking.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/discount")
@CrossOrigin("*")
public class ApiDiscountController {
    @Autowired
    public DiscountService discountService;

    @GetMapping
    public ResponseEntity<Map<String, String>> getDiscountById(@RequestParam("code") String code) {
        Discount discount = discountService.getDiscountByDiscountCode(code,1);

        Map<String, String> response = new HashMap<>();
        if (discount != null)
            response.put("discountValue", discount.getDiscountValue() + "");
        else
            response.put("discountValue", 0 + "");

        return ResponseEntity.ok(response);
    }

}
