package com.tourbooking.repository;

import com.tourbooking.model.TourDiscount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TourDiscountRepository extends JpaRepository<TourDiscount, Integer> {

    // Tìm kiếm mã giảm giá theo tourTimeId và discountId
    Optional<TourDiscount> findByTourTimeIdAndDiscountId(int tourTimeId, int discountId);

    // Xóa mã giảm giá theo tourTimeId và discountId
    void deleteByTourTimeIdAndDiscountId(int tourTimeId, int discountId);
    List<TourDiscount> findByTourTimeId(int tourTimeId);

}
