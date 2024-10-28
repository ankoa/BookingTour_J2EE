package com.tourbooking.controller;

import com.tourbooking.model.Customer;
import com.tourbooking.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class CustomerControllerAdmin {

    private static final Logger logger = LoggerFactory.getLogger(CustomerControllerAdmin.class);

    @Autowired
    private CustomerService customerService;

    // Lấy danh sách tất cả khách hàng
    @GetMapping("/customers")  // Đường dẫn đã chỉnh sửa
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

 // Lấy thông tin khách hàng theo ID
    @GetMapping("/customers/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable int id) {
        Optional<Customer> optionalCustomer = customerService.getCustomerById(id); // Nhận Optional<Customer>
        if (optionalCustomer.isPresent()) { // Kiểm tra xem khách hàng có tồn tại không
            return ResponseEntity.ok(optionalCustomer.get()); // Trả về khách hàng
        } else {
            logger.warn("Customer with ID {} not found", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Trả về mã lỗi 404
        }
    }

    // Thêm khách hàng mới
    @PostMapping("/customers") // Đường dẫn đã chỉnh sửa
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
        Customer createdCustomer = customerService.addCustomer(customer);
        if (createdCustomer != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
        } else {
            logger.warn("Customer with ID {} already exists", customer.getCustomerId());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    // Cập nhật thông tin khách hàng
    @PutMapping("/customers/{id}") // Đường dẫn đã chỉnh sửa
    public ResponseEntity<Customer> updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
        Customer updatedCustomer = customerService.updateCustomer(id, customer);
        if (updatedCustomer != null) {
            return ResponseEntity.ok(updatedCustomer);
        } else {
            logger.warn("Customer with ID {} not found for update", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Xóa khách hàng
    @DeleteMapping("/customers/{id}") // Đường dẫn đã chỉnh sửa
    public ResponseEntity<Void> deleteCustomer(@PathVariable int id) {
        boolean isDeleted = customerService.deleteCustomer(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            logger.warn("Customer with ID {} not found for deletion", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
