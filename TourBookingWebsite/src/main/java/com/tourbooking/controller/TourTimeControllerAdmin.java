package com.tourbooking.controller;

import com.tourbooking.model.TourTime;
import com.tourbooking.model.Tour;
import com.tourbooking.service.TourTimeService;
import com.tourbooking.service.TourService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@RequestMapping("/admin")

@Controller
public class TourTimeControllerAdmin {
    private static final Logger logger = LoggerFactory.getLogger(TourTimeControllerAdmin.class);

    @Autowired
    private TourTimeService tourTimeService;

    @Autowired
    private TourService tourService;

    @GetMapping("/tour-times")
    public String getTourTimeList(Model model) {
        return "admin/tour-time-list";
    }

    @GetMapping("/tour-times/list")
    public ResponseEntity<List<TourTime>> getAllTourTimes() {
        List<TourTime> tourTimes = tourTimeService.getAllTourTimes();
        logger.info("Loaded tour times: {}", tourTimes);
        return ResponseEntity.ok(tourTimes);
    }

    @GetMapping("/tour-times/{id}")
    public ResponseEntity<TourTime> getTourTimeById(@PathVariable String id) {
        return tourTimeService.getTourTimeById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

   /* @PostMapping("/tour-times/add")
    public ResponseEntity<Map<String, String>> addTourTime(@RequestBody Map<String, Object> tourTimeData) {
        Map<String, String> response = new HashMap<>();
        System.out.println("hahahaha");
        try {
            // Lấy thông tin từ request
            String tourTimeCode = (String) tourTimeData.get("tourTimeCode");
            String timeName = (String) tourTimeData.get("timeName");
            String departureTimeStr = (String) tourTimeData.get("departureTime");
            String returnTimeStr = (String) tourTimeData.get("returnTime");
            int quantity = (int) tourTimeData.get("quantity");
            int priceAdult = (int) tourTimeData.get("priceAdult");
            int priceChild = (int) tourTimeData.get("priceChild");
            String note = (String) tourTimeData.get("note");
            int status = (int) tourTimeData.get("status");
            int tourId = (int) tourTimeData.get("tourId");

            // Chuyển đổi thời gian từ chuỗi sang LocalDateTime
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime departureTime = LocalDateTime.parse(departureTimeStr, formatter);
            LocalDateTime returnTime = LocalDateTime.parse(returnTimeStr, formatter);

            // Tìm Tour tương ứng
            Optional<Tour> tourOptional = tourService.getTourByIdInt(tourId);
            if (!tourOptional.isPresent()) {
                response.put("message", "Tour không tồn tại.");
                return ResponseEntity.badRequest().body(response);
            }
            Tour tour = tourOptional.get();

            // Tạo đối tượng TourTime mới
            TourTime tourTime = new TourTime();
            tourTime.setTourTimeCode(tourTimeCode);
            tourTime.setTimeName(timeName);
            tourTime.setDepartureTime(departureTime);
            tourTime.setReturnTime(returnTime);
            tourTime.setQuantity(quantity);
            tourTime.setPriceAdult(priceAdult);
            tourTime.setPriceChild(priceChild);
            tourTime.setNote(note);
            tourTime.setStatus(status);
            tourTime.setTour(tour);  // Liên kết với Tour

            // Lưu TourTime vào cơ sở dữ liệu
            tourTimeService.addTourTime(tourTime);

            // Trả về phản hồi thành công
            response.put("message", "Thêm thời gian tour thành công.");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // Xử lý lỗi
            response.put("message", "Có lỗi xảy ra: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }*/
    @PostMapping("/tour-times/add")
    public ResponseEntity<Map<String, String>> addTourTime(@RequestBody Map<String, Object> tourTimeData) {
        Map<String, String> response = new HashMap<>();
        try {
            // In ra toàn bộ dữ liệu nhận từ request
            System.out.println("tourTimeData: " + tourTimeData);

            // Lấy thông tin từ request và kiểm tra giá trị null
            String tourTimeCode = (String) tourTimeData.get("tourTimeCode");
            String timeName = (String) tourTimeData.get("timeName");
            String departureTimeStr = (String) tourTimeData.get("departureTime");
            String returnTimeStr = (String) tourTimeData.get("returnTime");

            // In ra các giá trị cụ thể
            System.out.println("Tour Time Code: " + tourTimeCode);
            System.out.println("Time Name: " + timeName);
            System.out.println("Departure Time: " + departureTimeStr);
            System.out.println("Return Time: " + returnTimeStr);

            // Kiểm tra các trường bắt buộc
            if (tourTimeCode == null || timeName == null || departureTimeStr == null || returnTimeStr == null) {
                response.put("message", "Các trường mã thời gian, tên thời gian, thời gian khởi hành và thời gian kết thúc không được bỏ trống.");
                return ResponseEntity.badRequest().body(response);
            }

            // Chuyển đổi giá trị từ String sang Integer (kiểm tra null)
            Integer quantity = (tourTimeData.get("quantity") != null) ? Integer.parseInt(tourTimeData.get("quantity").toString()) : null;
            Integer priceAdult = (tourTimeData.get("priceAdult") != null) ? Integer.parseInt(tourTimeData.get("priceAdult").toString()) : null;
            Integer priceChild = (tourTimeData.get("priceChild") != null) ? Integer.parseInt(tourTimeData.get("priceChild").toString()) : null;
            String note = (String) tourTimeData.get("note");

            // In ra giá trị quantity, priceAdult, priceChild và note
            System.out.println("Quantity: " + quantity);
            System.out.println("Price Adult: " + priceAdult);
            System.out.println("Price Child: " + priceChild);
            System.out.println("Note: " + note);

            // Chuyển đổi thời gian từ chuỗi sang LocalDateTime
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime departureTime = null;
            LocalDateTime returnTime = null;
            try {
                departureTime = LocalDateTime.parse(departureTimeStr, formatter);
                returnTime = LocalDateTime.parse(returnTimeStr, formatter);
            } catch (DateTimeParseException e) {
                response.put("message", "Lỗi định dạng thời gian. Vui lòng sử dụng định dạng 'yyyy-MM-dd HH:mm:ss'.");
                return ResponseEntity.badRequest().body(response);
            }

            // In ra thời gian sau khi chuyển đổi
            System.out.println("Converted Departure Time: " + departureTime);
            System.out.println("Converted Return Time: " + returnTime);

            Integer tourId = (tourTimeData.get("tourId") != null) ? Integer.parseInt(tourTimeData.get("tourId").toString()) : null;

            // Kiểm tra tồn tại của Tour
            if (tourId == null) {
                response.put("message", "Tour ID không hợp lệ.");
                return ResponseEntity.badRequest().body(response);
            }

            Optional<Tour> tourOptional = tourService.getTourByIdInt(1);
            if (!tourOptional.isPresent()) {
                response.put("message", "Tour không tồn tại.");
                return ResponseEntity.badRequest().body(response);
            }

            Tour tour = tourOptional.get();

            // Tạo đối tượng TourTime mới
            TourTime tourTime = new TourTime();
            tourTime.setTourTimeCode(tourTimeCode);
            tourTime.setTimeName(timeName);
            tourTime.setDepartureTime(departureTime);
            tourTime.setReturnTime(returnTime);
            tourTime.setQuantity(quantity);
            tourTime.setPriceAdult(priceAdult);
            tourTime.setPriceChild(priceChild);
            tourTime.setNote(note);
            tourTime.setStatus(1);  // Trạng thái mặc định là 1
            tourTime.setTour(tour);  // Liên kết với Tour

            // Lưu TourTime vào cơ sở dữ liệu
            try {
                tourTimeService.addTourTime(tourTime);
            } catch (RuntimeException e) {
                response.put("message", "Có lỗi xảy ra khi lưu thông tin thời gian tour: " + e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }

            // Trả về phản hồi thành công
            response.put("success", "true");  // Đảm bảo rằng success là một trường trong response
            response.put("message", "Thêm thời gian tour thành công.");
            return ResponseEntity.ok(response);


        } catch (Exception e) {
            // Ghi log chi tiết lỗi
            e.printStackTrace();  // In ra chi tiết lỗi

            // Trả về phản hồi lỗi chi tiết
            response.put("success", "false");  // Đảm bảo có trường success và set giá trị false khi có lỗi
            response.put("message", "Có lỗi xảy ra: " + e.getMessage());  // Thêm thông điệp lỗi chi tiết
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

        }
    }
    @PutMapping("/tour-times/update/{id}")
    public ResponseEntity<Map<String, Object>> updateTourTime(@PathVariable("id") Integer id, @RequestBody Map<String, Object> tourTimeData) {
        Map<String, Object> response = new HashMap<>();
        try {
            System.out.println("tourTimeData: " + tourTimeData);

            // Lấy thông tin từ request và kiểm tra giá trị null
            String tourTimeCode = (String) tourTimeData.get("tourTimeCode");
            String timeName = (String) tourTimeData.get("timeName");

            System.out.println("Tour Time Code: " + tourTimeCode);
            System.out.println("Time Name: " + timeName);

            if (tourTimeCode == null || timeName == null) {
                response.put("success", false);
                response.put("message", "Các trường mã thời gian và tên thời gian không được bỏ trống.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            TourTime tourTime = tourTimeService.getTourTimeByIdINT(id);
            if (tourTime == null) {
                response.put("success", false);
                response.put("message", "Không tìm thấy thời gian tour với ID " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            TourTime existingTourTime = tourTime;

            if (tourTimeCode != null) existingTourTime.setTourTimeCode(tourTimeCode);
            if (timeName != null) existingTourTime.setTimeName(timeName);

            Integer status = (tourTimeData.get("status") != null) ? Integer.parseInt(tourTimeData.get("status").toString()) : null;
            if (status != null) existingTourTime.setStatus(status);

            Integer quantity = (tourTimeData.get("quantity") != null) ? Integer.parseInt(tourTimeData.get("quantity").toString()) : null;
            Integer priceAdult = (tourTimeData.get("priceAdult") != null) ? Integer.parseInt(tourTimeData.get("priceAdult").toString()) : null;
            Integer priceChild = (tourTimeData.get("priceChild") != null) ? Integer.parseInt(tourTimeData.get("priceChild").toString()) : null;
            String note = (String) tourTimeData.get("note");

            if (quantity != null) existingTourTime.setQuantity(quantity);
            if (priceAdult != null) existingTourTime.setPriceAdult(priceAdult);
            if (priceChild != null) existingTourTime.setPriceChild(priceChild);
            if (note != null) existingTourTime.setNote(note);

            try {
                tourTimeService.updateTourTime(existingTourTime);
            } catch (RuntimeException e) {
                response.put("success", false);
                response.put("message", "Có lỗi xảy ra khi cập nhật thời gian tour: " + e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }

            response.put("success", true);
            response.put("message", "Cập nhật thời gian tour thành công.");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();

            response.put("success", false);
            response.put("message", "Có lỗi xảy ra: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }








    @PostMapping("/tour-times/delete")
    public ResponseEntity<?> deleteTourTime(@RequestParam Integer tourTimeId) {
        try {
            // Cập nhật trạng thái về 0
            tourTimeService.updateStatusToZero(tourTimeId);
            return ResponseEntity.ok("TourTime has been updated (status set to 0).");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred: " + e.getMessage());
        }
    }


    @GetMapping("/gettourtimesbytourID/{tourId}")
    public ResponseEntity<List<TourTime>> getTourTimesByTourId(@PathVariable int tourId) {
        List<TourTime> tourTimes = tourTimeService.getTourTimesByTourId(tourId);
        return ResponseEntity.ok(tourTimes);
    }
    @GetMapping("/gettourtimesbyidadmin/{id}")
    public ResponseEntity<TourTime> getTourTimeByIdAdmin(@PathVariable String id) {
        // Gọi phương thức service đã được chỉnh sửa
        Optional<TourTime> tourTimeOptional = tourTimeService.getTourTimeByIdAdmin(id);
        
        if (tourTimeOptional.isPresent()) {
            return ResponseEntity.ok(tourTimeOptional.get()); // Trả về dữ liệu nếu tìm thấy
        } else {
            return ResponseEntity.notFound().build(); // Trả về lỗi 404 nếu không tìm thấy
        }
    }



}
