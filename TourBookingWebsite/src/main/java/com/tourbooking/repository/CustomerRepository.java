package com.tourbooking.repository;

import com.tourbooking.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    // Bạn có thể định nghĩa thêm các phương thức tùy chỉnh nếu cần
	@Modifying
    @Transactional
    @Query("UPDATE Customer a SET a.status = 0 WHERE a.customerId = :customerId")
    void deactivateCustomer(Integer customerId);
}
