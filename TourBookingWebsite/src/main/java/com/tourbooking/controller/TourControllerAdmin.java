package com.tourbooking.controller;

import com.tourbooking.model.Tour;
import com.tourbooking.model.TourImage;
import com.tourbooking.model.Category;
import com.tourbooking.service.TourService;
import com.tourbooking.service.TourImageService;
import com.tourbooking.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class TourControllerAdmin {

    @Autowired
    private TourService tourService;
    
    @Autowired
    private TourImageService tourImageService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/tour-all")
    public String getTourList(Model model) {
    	return "tour-all";
    }
    @GetMapping("/tours")
    public ResponseEntity<List<Tour>> getAllTours() {
        List<Tour> tours = tourService.getAllTours();
        if (tours != null) {
            return new ResponseEntity<>(tours, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getCategories();
        
        // In danh sách thể loại ra terminal
        if (categories != null) {
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





    // Lấy tour theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Tour> getTourById(@PathVariable int id) {
        Optional<Tour> tour = tourService.getTourById(id);
        return tour.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

 // Hiển thị trang tạo tour
    @GetMapping("/tour-add")
    public String addTourPage(Model model) {
        // Tạo một đối tượng Tour mới
        model.addAttribute("tour", new Tour());

        // Lấy danh sách các thể loại từ CategoryService
        List<Category> categoryList = categoryService.getCategories();
        model.addAttribute("categories", categoryList); // Thêm danh sách thể loại vào model

        return "tour-add"; 
    }
    @PostMapping("/tour-save")
    public ResponseEntity<String> addTourWithImages(@RequestParam("tourName") String tourName,
                                                     @RequestParam("loaiTour") int maloai,
                                                     @RequestParam("duration") int duration,
                                                     @RequestParam("price") double price,
                                                     @RequestParam("description") String description,
                                                     @RequestParam("images") MultipartFile[] images) {
        // Tạo đối tượng Tour từ các tham số
        /*Tour tour = new Tour();
        tour.setTourName(tourName);
        tour.setCategoryId(maloai);
        tour.setDuration(duration);
        tour.setPrice(price);
        tour.setDescription(description);
        System.out.println("okokok");
        tourService.addTour(tour);
        System.out.println("okokok2");*/

        /*
        // Lưu tour và hình ảnh
        Tour savedTour = tourService.addTour(tour);
        for (MultipartFile imageFile : images) {
            TourImage tourImage = new TourImage();
            String imageUrl = tourImageService.saveImage(imageFile);
            
            if (imageUrl != null) {
                tourImage.setTourId(savedTour.getTourId());
                tourImage.setImageUrl(imageUrl);
                tourImageService.addTourImage(tourImage);
            }
        }*/
        return ResponseEntity.ok("Tour và hình ảnh đã được thêm thành công");
    }

    // Cập nhật tour
    @PutMapping("/{id}")
    public ResponseEntity<Tour> updateTour(@PathVariable int id, @RequestBody Tour tour) {
        Tour updatedTour = tourService.updateTour(id, tour);
        return updatedTour != null ? ResponseEntity.ok(updatedTour) : ResponseEntity.notFound().build();
    }

    // Xóa tour
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTour(@PathVariable int id) {
        tourService.deleteTour(id);
        return ResponseEntity.noContent().build();
    }
}
