package com.tourbooking.rest;

import com.tourbooking.dto.request.CreateAccountRequest;
import com.tourbooking.dto.request.UpdateAccountRequest;
import com.tourbooking.dto.response.CreateNewAccountResponse;
import com.tourbooking.security.CustomUserDetails;
import com.tourbooking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class ApiAuthController {
    @Autowired
    AccountService accountService;

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
        CreateNewAccountResponse account = accountService.UpdateAccount(updateAccountRequest,user.getAccount());
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
}
