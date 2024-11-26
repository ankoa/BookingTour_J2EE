package com.tourbooking.controller;

import com.tourbooking.model.Category;
import com.tourbooking.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class CategoryControllerAdmin {

    @Autowired
    private CategoryService categoryService;

    /**
     * Lấy danh sách tất cả thể loại.
     *
     * @return danh sách thể loại.
     */
    @GetMapping("/categories/list")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getCategories();
        return ResponseEntity.ok(categories);
    }

    /**
     * Lấy thông tin thể loại theo ID.
     *
     * @param id ID của thể loại.
     * @return thông tin thể loại.
     */
    @GetMapping("/categories/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable int id) {
        Optional<Category> category = categoryService.getCategoryById(id);
        return category.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Thêm thể loại mới.
     *
     * @param category thông tin thể loại cần thêm.
     * @return thể loại vừa được thêm.
     */
    @PostMapping("/categories/add")
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        Category createdCategory = categoryService.addCategory(category);
        return ResponseEntity.ok(createdCategory);
    }

    /**
     * Cập nhật thông tin thể loại.
     *
     * @param id ID của thể loại cần cập nhật.
     * @param updatedCategory thông tin mới của thể loại.
     * @return thể loại đã được cập nhật.
     */
    @PutMapping("/categories/update/{id}")
    public ResponseEntity<Category> updateCategory(
            @PathVariable int id,
            @RequestBody Category updatedCategory) {
        Category updated = categoryService.updateCategory(id, updatedCategory);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Xóa mềm thể loại (thay đổi trạng thái từ 1 sang 0).
     *
     * @param id ID của thể loại cần xóa.
     * @return trạng thái xóa.
     */
    @DeleteMapping("/categories/delete/{id}")
    public ResponseEntity<Void> softDeleteCategory(@PathVariable int id) {
        Optional<Category> category = categoryService.getCategoryById(id);
        if (category.isPresent()) {
            Category existingCategory = category.get();
            existingCategory.setStatus(0); // Đặt trạng thái thành 0 (ngừng hoạt động).
            categoryService.updateCategory(id, existingCategory);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
