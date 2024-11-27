package com.tourbooking.repository;

import com.tourbooking.model.TourDiscount;
import com.tourbooking.model.TourDiscountId;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;
import java.util.Optional;

public interface TourDiscountRepository extends JpaRepository<TourDiscount, TourDiscountId> {

    // Tìm kiếm mã giảm giá theo tourTimeId và discountId
    Optional<TourDiscount> findByTourTimeIdAndDiscountId(int tourTimeId, int discountId);

    // Xóa mã giảm giá theo tourTimeId và discountId
    @Modifying
    @Transactional
    void deleteByTourTimeIdAndDiscountId(int tourTimeId, int discountId);

    List<TourDiscount> findByTourTimeId(int tourTimeId);
}