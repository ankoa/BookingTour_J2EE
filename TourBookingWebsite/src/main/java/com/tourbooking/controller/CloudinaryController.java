	package com.tourbooking.controller;
	
	import com.tourbooking.service.CloudinaryService;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.http.ResponseEntity;
	import org.springframework.web.bind.annotation.*;
	import org.springframework.web.multipart.MultipartFile;
	
	@RestController
	@RequestMapping("/api/images")
	public class CloudinaryController {
	
	    @Autowired
	    private CloudinaryService cloudinaryService;
	
	    /**
	     * API upload ảnh
	     *
	     * @param file Multipart file được gửi từ người dùng
	     * @return URL của ảnh sau khi upload
	     */
	    @PostMapping("/upload")
	    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
	        try {
	            // Upload file trực tiếp lên Cloudinary và nhận URL ảnh
	            String imageUrl = cloudinaryService.uploadImage(file);  // Dịch vụ xử lý upload lên Cloudinary
	
	            // Trả về URL ảnh
	            return ResponseEntity.ok(imageUrl);
	        } catch (Exception e) {
	            // Trả về lỗi nếu upload thất bại
	            return ResponseEntity.badRequest().body("Upload failed: " + e.getMessage());
	        }
	    }
	
	}
