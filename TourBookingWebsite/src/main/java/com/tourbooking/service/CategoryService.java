package com.tourbooking.service;

import com.tourbooking.model.Category;
import com.tourbooking.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    public List<Category> getCategories() {
        return categoryRepository.findAllCategories();
    }


    // Lấy thể loại theo ID
    public Optional<Category> getCategoryById(int id) {
        return categoryRepository.findById(id);
    }

    // Thêm thể loại mới
    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    // Cập nhật thể loại
    public Category updateCategory(int id, Category category) {
        // Kiểm tra xem thể loại có tồn tại không
        if (categoryRepository.existsById(id)) {
            category.setCategoryId(id); // Đặt ID cho thể loại
            return categoryRepository.save(category);
        }
        return null; // Nếu không tồn tại, trả về null
    }

    // Xóa thể loại
    public void deleteCategory(int id) {
        categoryRepository.deleteById(id);
    }
    
}
