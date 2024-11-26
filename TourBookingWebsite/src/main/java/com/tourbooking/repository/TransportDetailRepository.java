package com.tourbooking.repository;

import com.tourbooking.model.TransportDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportDetailRepository extends JpaRepository<TransportDetail, Integer> {
    // Có thể thêm các phương thức truy vấn tùy chỉnh tại đây nếu cần
}
