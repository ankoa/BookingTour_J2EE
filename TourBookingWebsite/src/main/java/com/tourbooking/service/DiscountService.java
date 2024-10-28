package com.tourbooking.service;

import com.tourbooking.model.Discount;
import com.tourbooking.repository.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscountService {

    @Autowired
    public DiscountRepository discountRepository;

    public Discount getDiscountByDiscountCode(String code) {
        return discountRepository.findByDiscountCode(code);
    }
}
