package com.tourbooking.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.tourbooking.model.Account;
import com.tourbooking.model.Customer;
import com.tourbooking.service.AccountService;
import com.tourbooking.service.CustomerService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AccountControllerAdmin {

    private static final Logger logger = LoggerFactory.getLogger(AccountControllerAdmin.class);
    private final AccountService accountService;
    private final CustomerService customerService;

    @Autowired
    public AccountControllerAdmin(AccountService accountService, CustomerService customerService) {
        this.accountService = accountService;
        this.customerService = customerService;
    }

    @GetMapping("/accounts")
    public String getAllAccounts(Model model) {
        List<Account> accounts = accountService.getAllAccounts();
        if (accounts.isEmpty()) {
            logger.warn("No accounts found.");
            model.addAttribute("message", "No accounts available.");
        } else {
            logger.info("Total accounts: {}", accounts.size());
            model.addAttribute("accounts", accounts);
        }
        return "admin/account-all"; 
    }

    @GetMapping("/accounts/listAccount")
    @ResponseBody
    public ResponseEntity<List<Account>> getAllAccountsJson() {
        List<Account> accounts = accountService.getAllAccounts();
        if (accounts.isEmpty()) {
            logger.warn("No accounts found.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(accounts); // Trả về 204 nếu không có tài khoản
        } else {
            logger.info("Total accounts: {}", accounts.size());
            return ResponseEntity.ok(accounts); // Trả về danh sách tài khoản với mã 200
        }
    }

    @GetMapping("/accounts/{id}")
    @ResponseBody
    public ResponseEntity<Optional<Account>> getAccountById(@PathVariable String id) {
        Optional<Account> account = accountService.getAccountById(id);
        if (account == null) {
            logger.warn("Account with ID {} not found.", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Trả về 404
        }
        return ResponseEntity.ok(account); // Trả về 200 với tài khoản
    }

    @PostMapping("/accounts/add")
    public ResponseEntity<Map<String, String>> addAccount(@RequestBody Map<String, Object> accountData) {
        Map<String, String> response = new HashMap<>();

        try {
            // Lấy các thông tin từ accountData
            String accountName = (String) accountData.get("accountName");
            String email = (String) accountData.get("email");
            String password = (String) accountData.get("password"); // Lấy mật khẩu

            // Chuyển đổi từ String sang Integer
            String statusString = (String) accountData.get("status");
            String customerIDString = (String) accountData.get("customerID");

            Integer status = statusString != null && !statusString.isEmpty() ? Integer.valueOf(statusString) : null;
            Integer customerID = customerIDString != null && !customerIDString.isEmpty() ? Integer.valueOf(customerIDString) : null;

            // Kiểm tra xem tất cả các trường cần thiết đều có giá trị
            if (accountName == null || accountName.isEmpty() ||
                email == null || email.isEmpty() ||
                password == null || password.isEmpty() || // Kiểm tra mật khẩu
                status == null || customerID == null) {
                response.put("message", "Vui lòng điền đầy đủ thông tin!");
                return ResponseEntity.badRequest().body(response);
            }

            // Kiểm tra tính hợp lệ của địa chỉ email
            if (!isValidEmail(email)) {
                response.put("message", "Địa chỉ email không hợp lệ!");
                return ResponseEntity.badRequest().body(response);
            }

            // Kiểm tra xem customerID đã tồn tại trong tài khoản nào chưa
            if (accountService.doesCustomerIDExist(customerID)) {
                response.put("message", "CustomerID này đã được sử dụng cho một tài khoản khác!");
                return ResponseEntity.badRequest().body(response);
            }

            // Lấy Customer từ ID
            Optional<Customer> optionalCustomer = customerService.getCustomerByIdAdmin(customerID);
            if (!optionalCustomer.isPresent()) {
                response.put("message", "Khách hàng không tồn tại!");
                return ResponseEntity.badRequest().body(response);
            }

            Customer customer = optionalCustomer.get();

            // Tạo đối tượng Account mới
            Account newAccount = new Account();
            newAccount.setAccountName(accountName);
            newAccount.setEmail(email);
            newAccount.setStatus(status);
            newAccount.setCustomer(customer); // Gán khách hàng vào tài khoản

            // Mã hóa mật khẩu trước khi lưu
            //String encodedPassword = passwordEncoder.encode(password); // Sử dụng PasswordEncoder để mã hóa
            newAccount.setPassword(password); // Thiết lập mật khẩu đã mã hóa
            newAccount.setTime(LocalDateTime.now()); // Thiết lập thời gian hiện tại
            // Gọi service để thêm tài khoản
            boolean added = accountService.addAccount(newAccount);
            if (added) {
                logger.info("Account with name {} added successfully.", accountName);
                response.put("message", "Tài khoản đã được thêm thành công!");
                return ResponseEntity.ok(response);
            } else {
                logger.warn("Failed to add account.");
                response.put("message", "Thêm tài khoản thất bại!");
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




    // Phương thức kiểm tra tính hợp lệ của email
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return email.matches(emailRegex);
    }


    @GetMapping("/account-edit")
    public String getAccountEditPage() {
        return "admin/account-edit"; 
    }

    @PutMapping("/accounts/update/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> updateAccount(@PathVariable Integer id, @RequestBody Map<String, Object> accountData) {
        Map<String, String> response = new HashMap<>();

        try {
            // Lấy các thông tin từ accountData
            String accountId = (String) accountData.get("accountId");

            String accountName = (String) accountData.get("accountName");
            String email = (String) accountData.get("email");
            String password = (String) accountData.get("password"); // Lấy mật khẩu

            String statusString = (String) accountData.get("status");
            Integer status = statusString != null && !statusString.isEmpty() ? Integer.valueOf(statusString) : null;

            // Kiểm tra xem tất cả các trường cần thiết đều có giá trị
            if (accountName == null || accountName.isEmpty() ||
                email == null || email.isEmpty() ||
                password == null || password.isEmpty() || // Kiểm tra mật khẩu
                status == null) {
                response.put("message", "Vui lòng điền đầy đủ thông tin!");
                return ResponseEntity.badRequest().body(response);
            }

            // Kiểm tra tính hợp lệ của địa chỉ email
            if (!isValidEmail(email)) {
                response.put("message", "Địa chỉ email không hợp lệ!");
                return ResponseEntity.badRequest().body(response);
            }

            // Lấy Account từ ID
            Optional<Account> optionalAccount = accountService.getAccountById(accountId);
            if (!optionalAccount.isPresent()) {
                response.put("message", "Tài khoản không tồn tại!");
                return ResponseEntity.badRequest().body(response);
            }

            Account existingAccount = optionalAccount.get();
            existingAccount.setAccountName(accountName);
            existingAccount.setEmail(email);
            existingAccount.setStatus(status);

            // Mã hóa mật khẩu trước khi cập nhật
            existingAccount.setPassword(password); // Sử dụng mật khẩu đã mã hóa
            existingAccount.setTime(LocalDateTime.now()); // Cập nhật thời gian

            // Gọi service để cập nhật tài khoản
            boolean updated = accountService.updateAccount(existingAccount);
            if (updated) {
                logger.info("Account with ID {} updated successfully.", existingAccount.getAccountId());
                response.put("message", "Tài khoản đã được cập nhật thành công!");
                return ResponseEntity.ok(response);
            } else {
                logger.warn("Failed to update account with ID {}.", existingAccount.getAccountId());
                response.put("message", "Cập nhật tài khoản thất bại!");
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

    @DeleteMapping("/accounts/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> deactivateAccount(@PathVariable String id) {
        boolean deactivated = accountService.deleteAccount(id);
        if (deactivated) {
            logger.info("Account with ID {} deactivated successfully.", id);
            return ResponseEntity.ok("Account deactivated successfully.");
        } else {
            logger.warn("Failed to deactivate account with ID {}.", id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to deactivate account.");
        }
    }

    @GetMapping("/accounts/checkCustomerID/{customerID}")
    @ResponseBody
    public ResponseEntity<String> checkCustomerID(@PathVariable Integer customerID) {
        boolean exists = accountService.doesCustomerIDExist(customerID);
        if (exists) {
            logger.info("CustomerID {} exists.", customerID);
            return ResponseEntity.ok("CustomerID exists.");
        } else {
            logger.warn("CustomerID {} does not exist.", customerID);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CustomerID does not exist.");
        }
    }

}
