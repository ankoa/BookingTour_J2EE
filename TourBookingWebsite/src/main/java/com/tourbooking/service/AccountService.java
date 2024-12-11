package com.tourbooking.service;

import com.tourbooking.dto.request.CreateAccountRequest;
import com.tourbooking.dto.request.UpdateAccountRequest;
import com.tourbooking.dto.response.CreateNewAccountResponse;
import com.tourbooking.mapper.AccountMapper;
import com.tourbooking.model.Customer;
import com.tourbooking.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.tourbooking.model.Account;
import com.tourbooking.repository.AccountRepository;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    AccountMapper accountMapper;

    @Autowired
    public AccountService(AccountRepository accountRepository, CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    // Lấy tất cả các tài khoản
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Optional<Account> getAccountById(String accountId) {
        return accountRepository.findById(Integer.parseInt(accountId));
    }

    public Account getAccountByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    public Account getAccountByUsername(String accountName) {
        return accountRepository.findByAccountName(accountName);
    }
    // public Account getAccountById(String accountId) {
    //     return accountRepository.findById(Integer.parseInt(accountId)).orElse(null);
    // }

    public Map<String, Object> validateAccountBeforeCreate(CreateAccountRequest createAccountRequest) {
        Map<String, Object> response = new HashMap<>();
        List<Map<String, String>> errors = new ArrayList<>();

        if (accountRepository.existsByAccountName(createAccountRequest.getUsername())) {
            Map<String, String> usernameError = new HashMap<>();
            usernameError.put("field", "username");
            usernameError.put("message", "Tên đăng nhập đã tồn tại!");
            errors.add(usernameError);
        }

        // Kiểm tra email đã tồn tại chưa
        if (accountRepository.existsByEmail(createAccountRequest.getEmail())) {
            Map<String, String> emailError = new HashMap<>();
            emailError.put("field", "email");
            emailError.put("message", "Email đã tồn tại!");
            errors.add(emailError);
        }

        if (!errors.isEmpty()) {
            response.put("status", "error");
            response.put("errors", errors);
            return response;
        }
        return null;
    }

    public CreateNewAccountResponse CreateNewAccount(CreateAccountRequest request) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Customer customer = new Customer();
        customer.setCustomerName(request.getFullName());
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setAddress(request.getAddress());
        customer.setSex(request.getSex());
        customer.setBirthday(request.getBirthday());
        customer.setTime(new Date());
        customer.setStatus(1);
        try {
            customerRepository.save(customer);
            Account account = new Account();
            account.setCustomer(customer);
            account.setAccountName(request.getUsername());
            account.setPassword(passwordEncoder.encode(request.getPassword()));
            account.setEmail(request.getEmail());
            account.setStatus(1);
            account.setTime(LocalDateTime.now());
            account.setRole("ROLE_USER");
            accountRepository.save(account);
            return accountMapper.toAccountResponse(account);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public CreateNewAccountResponse UpdateAccount(UpdateAccountRequest request,Account account) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Customer customer =account.getCustomer();
        customer.setCustomerName(request.getFullName());
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setAddress(request.getAddress());
        customer.setSex(request.getSex());
        customer.setBirthday(request.getBirthday());
        try {
            customerRepository.save(customer);
            account.setCustomer(customer);
            account.setPassword(passwordEncoder.encode(request.getPassword()));
            accountRepository.save(account);
            return accountMapper.toAccountResponse(account);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public boolean addAccount(Account account) {
        try {
            // Kiểm tra nếu mật khẩu chưa được mã hóa
            if (!account.getPassword().startsWith("$2a$")) { // Kiểm tra định dạng của BCrypt
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                account.setPassword(passwordEncoder.encode(account.getPassword())); // Mã hóa mật khẩu
            }

            // Lưu tài khoản mới
            accountRepository.save(account);
            return true; // Trả về true nếu thêm thành công
        } catch (Exception e) {
            // Ghi log lỗi nếu cần
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi xảy ra
        }
    }

    public boolean updateAccount(Account account) {
        try {
            // Tìm tài khoản theo ID
            Account existingAccount = accountRepository.findById(account.getAccountId()).orElse(null);
            if (existingAccount != null) {
                // Cập nhật các thông tin khác
                existingAccount.setAccountName(account.getAccountName());
                existingAccount.setEmail(account.getEmail());
                existingAccount.setStatus(account.getStatus());
                
                // Kiểm tra nếu mật khẩu được thay đổi
                if (account.getPassword() != null && !account.getPassword().isEmpty()) {
                    // Nếu mật khẩu chưa mã hóa, thực hiện mã hóa
                    if (!account.getPassword().startsWith("$2a$")) { // Kiểm tra định dạng của BCrypt
                        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                        existingAccount.setPassword(passwordEncoder.encode(account.getPassword())); // Mã hóa mật khẩu
                    }
                }

                // Lưu thông tin cập nhật
                accountRepository.save(existingAccount);
                return true;
            }
            return false; // Tài khoản không tồn tại
        } catch (Exception e) {
            // Ghi log lỗi nếu cần
            e.printStackTrace();
            return false; // Xảy ra lỗi
        }
    }

    // Vô hiệu hóa tài khoản bằng cách cập nhật status thành 0
    public boolean deleteAccount(String accountId) {
        try {
            accountRepository.deactivateAccount(Integer.parseInt(accountId));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Kiểm tra sự tồn tại của customerID
    public boolean doesCustomerIDExist(Integer customerID) {
        return accountRepository.checkCustomerIDExists(customerID);
    }
}
