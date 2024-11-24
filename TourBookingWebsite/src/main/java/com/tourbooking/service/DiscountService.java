package com.tourbooking.service;

import com.tourbooking.model.Discount;
import com.tourbooking.repository.DiscountRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscountService {

    @Autowired
    public DiscountRepository discountRepository;

    public Discount getDiscountByDiscountCode(String code ,Integer status) {
        Discount discount = discountRepository.findByDiscountCode(code);
        if(status!=null && discount.getStatus()!=status) return null;
        return discount;
    }
 // Lấy tất cả mã giảm giá
    public List<Discount> getAllDiscounts() {
        return discountRepository.findAll();
    }

    // Lấy mã giảm giá theo ID
    public Discount getDiscountById(int discountId) {
        return discountRepository.findById(discountId).orElse(null);
    }

    // Cập nhật mã giảm giá
    public boolean updateDiscount(Discount discountDetails) {
        Discount discount = discountRepository.findById(discountDetails.getDiscountId()).orElse(null);
        if (discount != null) {
            // Cập nhật các trường hiện có trong Discount
            discount.setDiscountCode(discountDetails.getDiscountCode());
            discount.setDiscountValue(discountDetails.getDiscountValue());
            discount.setStartDate(discountDetails.getStartDate());
            discount.setEndDate(discountDetails.getEndDate());
            discount.setStatus(discountDetails.getStatus());
            discount.setNote(discountDetails.getNote());
            discountRepository.save(discount);
            return true;
        }
        return false;
    }

    // Xóa mã giảm giá
    public boolean deleteDiscount(String discountId) {
        try {
            discountRepository.deactivateDiscount(Integer.parseInt(discountId));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Thêm mã giảm giá mới
    public Discount addDiscount(Discount discount) {
        return discountRepository.save(discount);
    }
}
