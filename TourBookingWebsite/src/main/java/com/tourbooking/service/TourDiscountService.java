package com.tourbooking.service;

import com.tourbooking.model.TourDiscount;
import com.tourbooking.repository.TourDiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TourDiscountService {

    private final TourDiscountRepository tourDiscountRepository;

    @Autowired
    public TourDiscountService(TourDiscountRepository tourDiscountRepository) {
        this.tourDiscountRepository = tourDiscountRepository;
    }

    // Lấy tất cả các mã giảm giá liên kết với tour
    public List<TourDiscount> getAllTourDiscounts() {
        return tourDiscountRepository.findAll();
    }

    // Lấy mã giảm giá theo tourTimeId và discountId
    public Optional<TourDiscount> getTourDiscountById(int tourTimeId, int discountId) {
        return tourDiscountRepository.findByTourTimeIdAndDiscountId(tourTimeId, discountId);
    }

    // Thêm một mã giảm giá mới cho tour
    public TourDiscount addTourDiscount(TourDiscount tourDiscount) {
        return tourDiscountRepository.save(tourDiscount);
    }

    // Cập nhật mã giảm giá cho tour
    public TourDiscount updateTourDiscount(TourDiscount tourDiscount) {
        return tourDiscountRepository.save(tourDiscount);
    }

    // Xóa mã giảm giá theo tourTimeId và discountId
    public void deleteTourDiscount(int tourTimeId, int discountId) {
        tourDiscountRepository.deleteByTourTimeIdAndDiscountId(tourTimeId, discountId);
    }
    // Lấy danh sách mã giảm giá theo tourTimeId
    public List<TourDiscount> getTourDiscountsByTourTimeId(int tourTimeId) {
        return tourDiscountRepository.findByTourTimeId(tourTimeId);
    }
}
