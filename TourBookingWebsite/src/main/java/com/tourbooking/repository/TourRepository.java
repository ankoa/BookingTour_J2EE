package com.tourbooking.repository;

import com.tourbooking.model.Tour;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TourRepository extends JpaRepository<Tour, Integer> {
	@Query("SELECT t FROM Tour t WHERE LOWER(t.tourName) LIKE LOWER(CONCAT('%', :searchValue, '%'))")
    List<Tour> searchTours(@Param("searchValue") String searchValue);
	
    @Modifying
    @Transactional
    @Query("UPDATE Tour a SET a.status = 0 WHERE a.tourId = :tourId")
    void deleteTour(Integer tourId);
}
    // Bạn có thể thêm các phương thức truy vấn tùy chỉnh ở đây nếu cần
