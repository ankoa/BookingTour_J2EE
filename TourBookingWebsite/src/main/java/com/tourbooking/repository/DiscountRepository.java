package com.tourbooking.repository;

import com.tourbooking.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Integer> {
    public Discount findByDiscountCode(String discountCode);
}
