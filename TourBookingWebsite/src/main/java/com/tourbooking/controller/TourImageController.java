package com.tourbooking.controller;

import com.tourbooking.model.Tour;
import com.tourbooking.model.TourImage;
import com.tourbooking.service.TourImageService;
import com.tourbooking.service.TourService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class TourImageController {

    private final TourImageService tourImageService;
    private final TourService tourService;

    @Autowired
    public TourImageController(TourImageService tourImageService, TourService tourService) {
        this.tourImageService = tourImageService;
        this.tourService = tourService;
    }

    @GetMapping("/api/tour-images/{tourId}")
    public ResponseEntity<List<TourImage>> getTourImages(@PathVariable int tourId) {
        try {
            // In ra giá trị tourId nhận được từ request
            System.out.println("Received tourId: " + tourId);

            // Gọi service để lấy danh sách hình ảnh của tour
            List<TourImage> images = tourImageService.getTourImages(tourId);

            // In ra danh sách hình ảnh (hoặc thông tin chi tiết nếu cần thiết)

            // Kiểm tra nếu không có hình ảnh nào
            if (images.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No images found for this tour");
            }

            return new ResponseEntity<>(images, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            // Nếu có lỗi do tourId không hợp lệ hoặc không tìm thấy hình ảnh
            throw e; // Ném lại lỗi cho Spring xử lý
        } catch (Exception e) {
            // Nếu có lỗi không mong muốn
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", e);
        }
    }

    @PostMapping("/api/tour-images/save")
    public ResponseEntity<String> saveTourImage(@RequestBody Map<String, Object> data) {
        // In log để kiểm tra dữ liệu
        System.out.println("Dữ liệu nhận được từ client: " + data.toString());

        try {
            // Lấy dữ liệu từ Map
            int tourId = Integer.parseInt(String.valueOf(data.get("tourId"))); 
            int status = Integer.parseInt(String.valueOf(data.get("status"))); 
            String imageUrl = (String) data.get("imageUrl");

            // In các giá trị lấy từ Map
            /*System.out.println("Tour ID: " + tourId);
            System.out.println("Status: " + status);
            System.out.println("Image URL: " + imageUrl);*/

            // Tạo đối tượng TourImage và thiết lập các giá trị
            TourImage tourImage = new TourImage();
            tourImage.setTour(tourService.getTourById(String.valueOf(tourId)));  // Lấy tour từ dịch vụ TourService
            tourImage.setStatus(status);
            tourImage.setImageUrl(imageUrl);

            // Lưu thông tin ảnh vào cơ sở dữ liệu
            boolean isSaved = tourImageService.save(tourImage);

            if (isSaved) {
                return ResponseEntity.ok("Image saved successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                     .body("Image save failed.");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Ghi lỗi vào log
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Image save failed: " + e.getMessage());
        }
    }
    @PutMapping("/api/tour-images/update")
    public ResponseEntity<String> updateImage(@RequestBody Map<String, Object> data) {
    	
    	 int imageID = Integer.parseInt(String.valueOf(data.get("imageId"))); 

         int status = Integer.parseInt(String.valueOf(data.get("status"))); 
         String imageUrl = (String) data.get("imageUrl");
         
        try {
            // Cập nhật thông tin ảnh
            tourImageService.updateImage(imageID,imageUrl,status);
            return ResponseEntity.ok("Image updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating image: " + e.getMessage());
        }
    }

    @DeleteMapping("/api/tour-images/delete/{imageId}")
    public ResponseEntity<String> deleteImage(@PathVariable("imageId") int imageId) {
        try {
            // Gọi dịch vụ để xóa hình ảnh
            boolean isDeleted = tourImageService.deleteImage(imageId);
            
            // Kiểm tra kết quả trả về từ dịch vụ
            if (isDeleted) {
                return ResponseEntity.ok("Image deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image not found");
            }
        } catch (Exception e) {
            // Bắt lỗi và trả về mã lỗi 500 nếu có sự cố
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting image: " + e.getMessage());
        }
    }

    @GetMapping("/active-images/{tourId}")
    public ResponseEntity<?> getActiveImages(@PathVariable int tourId) {
        // Kiểm tra xem có ảnh nào có status = 1 không
        boolean hasActiveImages = tourImageService.hasActiveImages(tourId);
        
        // Trả về true nếu có ảnh với status = 1, false nếu không có
        return ResponseEntity.ok(hasActiveImages);
    }


    @GetMapping("/active-images/{tourId}/{imageId}")
    public ResponseEntity<Boolean> checkActiveImage(@PathVariable int tourId, @PathVariable int imageId) {
        boolean isMatching = tourImageService.checkActiveImageId(tourId, imageId);
        return ResponseEntity.ok(isMatching);
    }




}
