package com.tourbooking.repository;

import com.tourbooking.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    // Bạn có thể định nghĩa thêm các phương thức tùy chỉnh nếu cần
}
