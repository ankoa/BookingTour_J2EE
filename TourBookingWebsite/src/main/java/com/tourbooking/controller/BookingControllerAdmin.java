package com.tourbooking.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.tourbooking.model.Booking;
import com.tourbooking.model.Account;
import com.tourbooking.model.Customer;
import com.tourbooking.model.TourTime;
import com.tourbooking.service.AccountService;
import com.tourbooking.service.BookingService;
import com.tourbooking.service.CustomerService;
import com.tourbooking.service.TourTimeService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Controller
@RequestMapping("/admin")
public class BookingControllerAdmin {
    private static final Logger logger = LoggerFactory.getLogger(AccountControllerAdmin.class);

    private final BookingService bookingService;
    private final CustomerService customerService;
    private final TourTimeService tour_timeService;

    @Autowired
    public BookingControllerAdmin(BookingService bookingService, CustomerService customerService,
			TourTimeService tour_timeService) {
		this.bookingService = bookingService;
		this.customerService = customerService;
		this.tour_timeService = tour_timeService;
	}

	// Phương thức để mở giao diện booking
    @GetMapping("/booking")
    public String getBookingPage() {
        return "admin/booking-all"; 
    }

    @GetMapping("/booking/list-booking")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings(); // Lấy danh sách booking
        Map<String, Object> response = new HashMap<>();
        
        if (bookings.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Trả về mã trạng thái 204 nếu không có booking
        }

        response.put("bookings", bookings);
        response.put("size", bookings.size());

        return new ResponseEntity<>(response, HttpStatus.OK); // Trả về danh sách booking và size với mã trạng thái 200
    }

    @DeleteMapping("/bookings/delete-booking/{id}")
    @ResponseBody
    public ResponseEntity<String> deactivateBooking(@PathVariable String id) {
        try {
            Integer bookingId = Integer.parseInt(id);
            boolean deactivated = bookingService.deactivateBooking(bookingId);
            if (deactivated) {
                return ResponseEntity.ok("Booking deactivated successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to deactivate booking.");
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid booking ID.");
        }
    }
    @GetMapping("/bookings/{bookingId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getBookingById(@PathVariable Integer bookingId) {
        Optional<Booking> booking = bookingService.getBookingById(bookingId);

        Map<String, Object> response = new HashMap<>();
        if (booking.isPresent()) {
            response.put("booking", booking.get());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("message", "Booking not found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/bookings/add")
    public ResponseEntity<Map<String, String>> addBooking(@RequestBody Map<String, Object> bookingData) {
        Map<String, String> response = new HashMap<>();

        try {
            // Lấy các thông tin từ bookingData
            String customerIdString = (String) bookingData.get("customerId");
            String tourTimeIdString = (String) bookingData.get("tourTimeId");
            String totalPriceString = (String) bookingData.get("totalPrice");
            String adultCountString = (String) bookingData.get("adultCount");
            String childCountString = (String) bookingData.get("childCount");
            String statusString = (String) bookingData.get("status");

            // Chuyển đổi từ String sang Integer
            Integer customerId = customerIdString != null && !customerIdString.isEmpty() ? Integer.valueOf(customerIdString) : null;
            Integer tourTimeId = tourTimeIdString != null && !tourTimeIdString.isEmpty() ? Integer.valueOf(tourTimeIdString) : null;
            Double totalPrice = totalPriceString != null && !totalPriceString.isEmpty() ? Double.valueOf(totalPriceString) : null;
            Integer adultCount = adultCountString != null && !adultCountString.isEmpty() ? Integer.valueOf(adultCountString) : null;
            Integer childCount = childCountString != null && !childCountString.isEmpty() ? Integer.valueOf(childCountString) : null;
            Integer status = statusString != null && !statusString.isEmpty() ? Integer.valueOf(statusString) : null;

            // Kiểm tra xem tất cả các trường cần thiết đều có giá trị
            if (customerId == null || tourTimeId == null || totalPrice == null ||
                adultCount == null || childCount == null || status == null) {
                response.put("message", "Vui lòng điền đầy đủ thông tin!");
                return ResponseEntity.badRequest().body(response);
            }


            // Kiểm tra xem Customer và TourTime có tồn tại không
            Optional<Customer> customerOptional = customerService.getCustomerByIdAdmin(customerId);
            if (!customerOptional.isPresent()) {
                response.put("message", "Khách hàng không tồn tại!");
                return ResponseEntity.badRequest().body(response);
            }

            Optional<TourTime> tourTimeOptional = tour_timeService.getTourTimeById(tourTimeId+"");
            if (!tourTimeOptional.isPresent()) {
                response.put("message", "Thông tin thời gian tour không hợp lệ!");
                return ResponseEntity.badRequest().body(response);
            }

            // Lấy các đối tượng Customer và TourTime từ ID
            Customer customer = customerOptional.get();
            TourTime tourTime = tourTimeOptional.get();

            // Tạo đối tượng Booking mới
            Booking newBooking = new Booking();
            newBooking.setCustomer(customer);
            newBooking.setTourTime(tourTime);
            newBooking.setTotalPrice(totalPrice.intValue());  // Chuyển đổi Double thành int
            newBooking.setAdultCount(adultCount);
            newBooking.setChildCount(childCount);
            newBooking.setStatus(status);
            newBooking.setTime(LocalDateTime.now()); // Thiết lập thời gian booking

            // Gọi service để thêm booking
            boolean added = bookingService.addBooking(newBooking);
            if (added) {
                logger.info("Booking for customer ID {} added successfully.", customerId);
                response.put("message", "Booking đã được thêm thành công!");
                return ResponseEntity.ok(response);
            } else {
                logger.warn("Failed to add booking.");
                response.put("message", "Thêm booking thất bại!");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (IllegalArgumentException e) {
            response.put("message", "Dữ liệu đầu vào không hợp lệ: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("message", "Có lỗi xảy ra: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
     
    @GetMapping("/chart")
    public String showChart() {
        return "/admin/chart"; 
    }
    
   /*
    @GetMapping("/api/revenue/range")
    public List<Map<String, Object>> getRevenueInRange(@RequestParam String startDate, @RequestParam String endDate) {
        return bookingService.getRevenueInRange(startDate, endDate);
    }


    @GetMapping("/chart")
    public String showChart(Model model) {
        model.addAttribute("dataPointsList", bookingService.getMonthlyRevenueData());
        return "admin/chart";
    }*/
    /*@GetMapping("/chart")
    public String showChart(Model model) {
        // Dữ liệu mẫu cho biểu đồ
        model.addAttribute("labels", new String[]{"January", "February", "March", "April", "May", "June"});
        model.addAttribute("data", new int[]{65, 59, 80, 81, 56, 55});
        return "/admin/chart";
    }
    @GetMapping("/chart")
    public String showChart(Model model) {
        // Lấy dữ liệu doanh thu hàng tháng từ service
        List<Map<String, Object>> revenueData = bookingService.getMonthlyRevenue();

        // Tạo danh sách tháng và doanh thu từ kết quả truy vấn
        String[] months = new String[revenueData.size()];
        int[] revenue = new int[revenueData.size()];

        for (int i = 0; i < revenueData.size(); i++) {
            months[i] = (String) revenueData.get(i).get("month");
            revenue[i] = ((Number) revenueData.get(i).get("revenue")).intValue();
        }

        // Truyền dữ liệu vào model để hiển thị trên frontend
        model.addAttribute("months", months);
        model.addAttribute("revenue", revenue);

        return "/admin/chart"; // Trả về trang chart.html
    }*/

}