package com.tourbooking.repository;

import com.tourbooking.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    // Phương thức truy vấn tùy chỉnh để lấy tất cả các category theo SQL thuần túy
    @Query(value = "SELECT * FROM category ORDER BY category_name ASC", nativeQuery = true)
    List<Category> findAllCategories(); // Lấy danh sách category đã sắp xếp theo tên
}
