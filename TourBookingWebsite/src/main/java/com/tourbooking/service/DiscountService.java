package com.tourbooking.service;

import com.tourbooking.model.Discount;
import com.tourbooking.repository.DiscountRepository;
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
}
