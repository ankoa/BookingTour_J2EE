package com.tourbooking.controller;

import com.tourbooking.model.Tour;
import com.tourbooking.model.Category;
import com.tourbooking.service.TourService;
import com.tourbooking.service.CategoryService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class TourControllerAdmin {
    private static final Logger logger = LoggerFactory.getLogger(TourControllerAdmin.class);

    @Autowired
    private TourService tourService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/tour-all")
    public String getTourList(Model model) {
        return "admin/tour-all";
    }

    @GetMapping("/tours/list-tour")
    public ResponseEntity<List<Tour>> getAllTours() {
        List<Tour> tours = tourService.getAllTours();
        logger.info("Loaded tours: {}", tours);
        return ResponseEntity.ok(tours);
    }

    @GetMapping("/tours/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/tours/{id}")
    public ResponseEntity<Tour> getTourById(@PathVariable int id) {
        return tourService.getTourByIdInt(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/tours/tour-add")
    public String addTourPage(Model model) {
        model.addAttribute("tour", new Tour());
        List<Category> categoryList = categoryService.getCategories();
        model.addAttribute("categories", categoryList);
        return "admin/tour-add";
    }

    @GetMapping("/tours/search")
    public ResponseEntity<List<Tour>> searchTours(@RequestParam("searchValue") String searchValue) {
        List<Tour> tours = tourService.searchTours(searchValue);
        return ResponseEntity.ok(tours);
    }
/*
    @PostMapping("/tours/add-tour")
    public ResponseEntity<String> addTour(@RequestBody Map<String, Object> tourData) {
        try {
            // Lấy các giá trị từ map tourData với kiểm tra kiểu dữ liệu
            String tourName = (String) tourData.get("tourName");
            String tourDetail = (String) tourData.get("tourDetail");
            Integer categoryId = ((Number) tourData.get("category")).intValue(); // Lấy ID loại tour
            Integer status = ((Number) tourData.get("status")).intValue();
            String tourCode = (String) tourData.get("tourCode");
            String dayStay = (String) tourData.get("dayStay");

            // Lấy Category từ ID
            Optional<Category> optionalCategory = categoryService.getCategoryById(categoryId);
            if (!optionalCategory.isPresent()) {
                return ResponseEntity.badRequest().body("Category không tồn tại!"); // Thông báo lỗi
            }
            Category category = optionalCategory.get(); // Lấy đối tượng Category

            // Tạo đối tượng Tour mới và gán các giá trị
            Tour newTour = new Tour();
            newTour.setTourName(tourName);
            newTour.setTourDetail(tourDetail);
            newTour.setCategory(category); // Gán Category đã lấy từ cơ sở dữ liệu
            newTour.setStatus(status);
            newTour.setTourCode(tourCode);
            newTour.setDayStay(dayStay);

            // Lưu tour mới vào database
            tourService.addTour(newTour);

            return ResponseEntity.ok("Tour đã được thêm thành công!"); // Thông báo thành công
        } catch (Exception e) {
            // Xử lý ngoại lệ
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Có lỗi xảy ra: " + e.getMessage());
        }
    }
*/  
    @PostMapping("/tours/add-tour")
    public ResponseEntity<Map<String, String>> addTour(@RequestBody Map<String, Object> tourData) {
        Map<String, String> response = new HashMap<>();

        try {
            // Kiểm tra xem tất cả các trường cần thiết đều có giá trị
            String tourName = (String) tourData.get("tourName");
            String tourDetail = (String) tourData.get("tourDetail");
            Integer categoryId = ((Number) tourData.get("category")).intValue();
            Integer status = ((Number) tourData.get("status")).intValue();
            String tourCode = (String) tourData.get("tourCode");
            String dayStay = (String) tourData.get("dayStay");

            if (tourName == null || tourDetail == null || categoryId == null || 
                status == null || tourCode == null || dayStay == null) {
                response.put("message", "Vui lòng điền đầy đủ thông tin!");
                return ResponseEntity.badRequest().body(response);
            }

            // Lấy Category từ ID
            Optional<Category> optionalCategory = categoryService.getCategoryById(categoryId);
            
            if (!optionalCategory.isPresent()) {
                response.put("message", "Category không tồn tại!");
                return ResponseEntity.badRequest().body(response);
            }
            
            Category category = optionalCategory.get();

            // Tạo đối tượng Tour mới
            Tour newTour = new Tour();
            newTour.setTourName(tourName);
            newTour.setTourDetail(tourDetail);
            newTour.setCategory(category);
            newTour.setStatus(status);
            newTour.setTourCode(tourCode);
            newTour.setDayStay(dayStay);
            System.out.print("okokok");
            // Lưu tour mới vào database
            tourService.addTour(newTour);

            response.put("message", "Thêm tour thành công!");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("message", "Dữ liệu đầu vào không hợp lệ: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("message", "Có lỗi xảy ra: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }



    @PutMapping("/tours/update-tour")
    public ResponseEntity<Map<String, String>> updateTour(@RequestBody Map<String, Object> tourData) {
        Map<String, String> response = new HashMap<>();
        try {
            Integer tourID = ((Number) tourData.get("tourId")).intValue();
            String tourName = (String) tourData.get("tourName");
            String tourDetail = (String) tourData.get("tourDetail");
            Integer categoryId = ((Number) tourData.get("category")).intValue();
            Integer status = ((Number) tourData.get("status")).intValue();
            String tourCode = (String) tourData.get("tourCode");
            String dayStay = (String) tourData.get("dayStay");

            // Kiểm tra dữ liệu đầu vào
            System.out.println("Tour ID: " + tourID);
            System.out.println("Tour Name: " + tourName);
            System.out.println("Tour Detail: " + tourDetail);
            System.out.println("Category ID: " + categoryId);
            System.out.println("Status: " + status);
            System.out.println("Tour Code: " + tourCode);
            System.out.println("Day Stay: " + dayStay);

            if (tourName == null || tourDetail == null || categoryId == null ||
                status == null || tourCode == null || dayStay == null) {
                response.put("message", "Vui lòng điền đầy đủ thông tin!");
                return ResponseEntity.badRequest().body(response);
            }

            // Lấy category và tour
            Category category = categoryService.getCategoryById(categoryId)
                                  .orElseThrow(() -> new IllegalArgumentException("Category không tồn tại!"));
            Tour existingTour = tourService.getTourByIdInt(tourID)
                                 .orElseThrow(() -> new IllegalArgumentException("Tour không tồn tại!"));

            // Cập nhật các trường cho đối tượng tour hiện tại
            existingTour.setTourName(tourName);
            existingTour.setTourDetail(tourDetail);
            existingTour.setCategory(category);
            existingTour.setStatus(status);
            existingTour.setTourCode(tourCode);
            existingTour.setDayStay(dayStay);

            // Lưu tour đã cập nhật vào database
            Tour updatedTour = tourService.updateTour(tourID, existingTour);

            // Kiểm tra kết quả trả về và đưa ra thông báo phù hợp
            if (updatedTour != null) {
                response.put("message", "Cập nhật tour thành công!");
                return ResponseEntity.ok(response);
            } else {
                response.put("message", "Không tìm thấy tour để cập nhật.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (IllegalArgumentException e) {
            response.put("message", "Dữ liệu đầu vào không hợp lệ: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("message", "Có lỗi xảy ra: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }





    @DeleteMapping("/tours/delete-tour/{id}")
    public ResponseEntity<String> deleteTour(@PathVariable String id) {
        boolean isDeleted = tourService.deleteTour(Integer.parseInt(id));
        if (isDeleted) {
            return ResponseEntity.noContent().build(); // Xóa thành công
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tour không tồn tại."); // Không tìm thấy tour
        }
    }

}