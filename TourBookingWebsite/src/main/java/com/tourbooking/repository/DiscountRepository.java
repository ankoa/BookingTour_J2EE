package com.tourbooking.repository;

import com.tourbooking.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Integer> {
    public Discount findByDiscountCode(String discountCode);
    @Modifying
    @Transactional
    @Query("UPDATE Discount a SET a.status = 0 WHERE a.discountId = :discountId")
    void deactivateDiscount(Integer discountId);
}
