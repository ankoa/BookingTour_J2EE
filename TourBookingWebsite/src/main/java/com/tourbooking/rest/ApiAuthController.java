package com.tourbooking.rest;

import com.tourbooking.dto.request.CreateAccountRequest;
import com.tourbooking.dto.request.UpdateAccountRequest;
import com.tourbooking.dto.response.CreateNewAccountResponse;
import com.tourbooking.model.Account;
import com.tourbooking.security.CustomUserDetails;
import com.tourbooking.service.AccountService;
import com.tourbooking.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class ApiAuthController {
    @Autowired
    AccountService accountService;
    @Autowired
    private EmailService emailService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> Register(@RequestBody CreateAccountRequest createAccountRequest) {
        Map<String, Object> validationResponse = accountService.validateAccountBeforeCreate(createAccountRequest);
        if (validationResponse != null) {
            return ResponseEntity.status(HttpStatusCode.valueOf(409)).body(validationResponse);
        }
        Map<String, Object> response = new HashMap<>();
        CreateNewAccountResponse account = accountService.CreateNewAccount(createAccountRequest);
        if (account != null) {
            response.put("status", "success");
            response.put("account", account);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            response.put("status", "error");
            response.put("message", "Có lỗi xảy ra!");
            return ResponseEntity.status(HttpStatusCode.valueOf(409)).body(response);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<Map<String, Object>> Update(@RequestBody UpdateAccountRequest updateAccountRequest,
                                                      @AuthenticationPrincipal CustomUserDetails user) {
        Map<String, Object> response = new HashMap<>();
        CreateNewAccountResponse account = accountService.UpdateAccount(updateAccountRequest, user.getAccount());
        if (account != null) {
            response.put("status", "success");
            response.put("account", account);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            response.put("status", "error");
            response.put("message", "Có lỗi xảy ra!");
            return ResponseEntity.status(HttpStatusCode.valueOf(409)).body(response);
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, Object>> forgotPassword(@RequestParam String email) throws MessagingException {
        // Tạo token reset mật khẩu
        Map<String, Object> response = new HashMap<>();
        Account user = accountService.getAccountByEmail(email);
        if (user == null) {
            response.put("message", "Email không tồn tai");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        String token = accountService.generateResetToken(email);

        // Tạo link reset
        String resetLink = "http://localhost:8080/reset-password?token=" + token;
        response.put("message", "Reset password email sent.");

        // Gửi email
        emailService.sendForgotPasswordEmail(email, resetLink);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, Object>>  resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        accountService.resetPassword(token, newPassword);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Thay đổi mật khẩu thành công ");
        return ResponseEntity.ok(response);
    }
}
