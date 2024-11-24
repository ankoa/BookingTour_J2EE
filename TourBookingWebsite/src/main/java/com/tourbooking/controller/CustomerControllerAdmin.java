package com.tourbooking.controller;

import com.tourbooking.model.Account;
import com.tourbooking.model.Customer;
import com.tourbooking.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class CustomerControllerAdmin {

    private static final Logger logger = LoggerFactory.getLogger(CustomerControllerAdmin.class);

    @Autowired
    private CustomerService customerService;
    @Autowired
    public CustomerControllerAdmin(CustomerService customerService) {
        this.customerService = customerService;
    }

	/*
	 * // Lấy danh sách tất cả khách hàng
	 * 
	 * @GetMapping("/customers") // Đường dẫn đã chỉnh sửa public
	 * ResponseEntity<List<Customer>> getAllCustomers() { List<Customer> customers =
	 * customerService.getAllCustomers(); return ResponseEntity.ok(customers); }
	 * 
	 * // Lấy thông tin khách hàng theo ID
	 * 
	 * @GetMapping("/customers/{id}") public ResponseEntity<Customer>
	 * getCustomerById(@PathVariable int id) { Optional<Customer> optionalCustomer =
	 * customerService.getCustomerById(id); // Nhận Optional<Customer> if
	 * (optionalCustomer.isPresent()) { // Kiểm tra xem khách hàng có tồn tại không
	 * return ResponseEntity.ok(optionalCustomer.get()); // Trả về khách hàng } else
	 * { logger.warn("Customer with ID {} not found", id); return
	 * ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Trả về mã lỗi 404
	 * } }
	 * 
	 * // Thêm khách hàng mới
	 * 
	 * @PostMapping("/customers") // Đường dẫn đã chỉnh sửa public
	 * ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
	 * Customer createdCustomer = customerService.addCustomer(customer); if
	 * (createdCustomer != null) { return
	 * ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer); } else {
	 * logger.warn("Customer with ID {} already exists", customer.getCustomerId());
	 * return ResponseEntity.status(HttpStatus.CONFLICT).body(null); } }
	 * 
	 * // Cập nhật thông tin khách hàng
	 * 
	 * @PutMapping("/customers/{id}") // Đường dẫn đã chỉnh sửa public
	 * ResponseEntity<Customer> updateCustomer(@PathVariable int id, @RequestBody
	 * Customer customer) { Customer updatedCustomer =
	 * customerService.updateCustomer(id, customer); if (updatedCustomer != null) {
	 * return ResponseEntity.ok(updatedCustomer); } else {
	 * logger.warn("Customer with ID {} not found for update", id); return
	 * ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); } }
	 * 
	 * // Xóa khách hàng
	 * 
	 * @DeleteMapping("/customers/{id}") // Đường dẫn đã chỉnh sửa public
	 * ResponseEntity<Void> deleteCustomer(@PathVariable int id) { boolean isDeleted
	 * = customerService.deleteCustomer(id); if (isDeleted) { return
	 * ResponseEntity.noContent().build(); } else {
	 * logger.warn("Customer with ID {} not found for deletion", id); return
	 * ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); } }
	 */
//    @GetMapping("/customers")
//    public List<Customer> getAllCustomers(@RequestParam(required = false) String search, @RequestParam(required = false) Integer status) {
//        List<Customer> customers = customerService.getAllCustomers();
//        if (search != null) {
//            customers = customers.stream().filter(c -> c.getCustomerName().toLowerCase().contains(search.toLowerCase())).toList();
//        }
//        if (status != null) {
//            customers = customers.stream().filter(c -> c.getStatus() == status).toList();
//        }
//        return customers;
//    }
//    @GetMapping("/customers")
//    public String getAllCustomers(Model model) {
//        List<Customer> customers = customerService.getAllCustomers();
//        if (customers.isEmpty()) {
//            logger.warn("No accounts found.");
//            model.addAttribute("message", "No accounts available.");
//        } else {
//            logger.info("Total accounts: {}", customers.size());
//            model.addAttribute("accounts", customers);
//        }
//        return "admin/customer-all"; 
//    }
//    @GetMapping("/customers")
//    public String showCustomerPage(Model model, 
//                                    @RequestParam(required = false) String search, 
//                                    @RequestParam(required = false) Integer status) {
//        List<Customer> customers = customerService.getAllCustomers();
//        if (search != null) {
//            customers = customers.stream()
//                                 .filter(c -> c.getCustomerName().toLowerCase().contains(search.toLowerCase()))
//                                 .toList();
//        }
//        if (status != null) {
//            customers = customers.stream()
//                                 .filter(c -> c.getStatus() == status)
//                                 .toList();
//        }
//        model.addAttribute("customers", customers);
//        return "admin/customer-all"; // Tên file HTML trong thư mục templates
//    }
//
//
//    // Lấy thông tin khách hàng theo ID
//    @GetMapping("/customers/{id}")
//    public Optional<Customer> getCustomerById(@PathVariable int id) {
//        return customerService.getCustomerById(id);
//    }
//
//    // Thêm khách hàng mới
//    @PostMapping
//    public Customer addCustomer(@RequestBody Customer customer) {
//        return customerService.addCustomer(customer);
//    }
//
//    // Cập nhật khách hàng
//    @PutMapping("/customers/{id}")
//    public Customer updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
//        return customerService.updateCustomer(id, customer);
//    }
//
//    // Xóa khách hàng (chuyển trạng thái sang ngưng hoạt động)
//    @DeleteMapping("/customers/{id}")
//    public boolean deleteCustomer(@PathVariable int id) {
//        Optional<Customer> customer = customerService.getCustomerById(id);
//        if (customer.isPresent()) {
//            Customer updatedCustomer = customer.get();
//            updatedCustomer.setStatus(0); // Chuyển trạng thái thành ngưng hoạt động
//            customerService.updateCustomer(id, updatedCustomer);
//            return true;
//        }
//        return false;
//    }
    @GetMapping("/customers")
    public String getAllCustomers(Model model) {
        List<Customer> customers = customerService.getAllCustomers();
        logger.info("Total customers: {}", customers.size());
        model.addAttribute("customers", customers);
        return "admin/customer-all"; // Trang hiển thị danh sách khách hàng
    }

    @GetMapping("/customers/listCustomer")
    @ResponseBody
    public ResponseEntity<List<Customer>> getAllCustomersJson() {
        List<Customer> customers = customerService.getAllCustomers();
        if (customers.isEmpty()) {
            logger.warn("No customers found.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(customers);
        }
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/customers/{id}")
    @ResponseBody
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") int id) {
        Customer customer = customerService.getCustomerById(id);
        if (customer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(customer);
    }

    @PostMapping("/customers/add")
    @ResponseBody
    public ResponseEntity<String> addCustomer(@RequestBody Customer customer) {
        customerService.addCustomer(customer);
        return ResponseEntity.ok("Khách hàng đã được thêm thành công.");
    }

    @PutMapping("/customers/update/{id}")
    @ResponseBody
    public ResponseEntity<String> updateCustomer(@PathVariable("id") int id, @RequestBody Customer customer) {
        boolean updated = customerService.updateCustomer(customer);
        if (updated) {
            return ResponseEntity.ok("Cập nhật khách hàng thành công.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy khách hàng.");
        }
    }

    @DeleteMapping("/customers/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> deactivateCustomer(@PathVariable String id) {
        boolean deactivated = customerService.deactivateCustomer(id);
        if (deactivated) {
            return ResponseEntity.ok("Khách hàng đã được vô hiệu hóa thành công.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Vô hiệu hóa khách hàng thất bại.");
        }
    }
    
}
