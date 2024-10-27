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
import com.tourbooking.service.AccountService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AccountControllerAdmin {

    private static final Logger logger = LoggerFactory.getLogger(AccountControllerAdmin.class);
    private final AccountService accountService;

    @Autowired
    public AccountControllerAdmin(AccountService accountService) {
        this.accountService = accountService;
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
    public Account getAccountById(@PathVariable String id) {
        Account account = accountService.getAccountById(id);
        if (account == null) {
            logger.warn("Account with ID {} not found.", id);
        }
        return account;
    }
    @PostMapping("/accounts/add")
    @ResponseBody
    public ResponseEntity<String> addAccount(@RequestBody Account account) {
        boolean added = accountService.addAccount(account); // Gọi service để thêm tài khoản
        if (added) {
            logger.info("Account with ID {} added successfully.", account.getAccountId());
            return ResponseEntity.ok("Account added successfully.");
        } else {
            logger.warn("Failed to add account.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add account.");
        }
    }
    @GetMapping("/account-edit")
    public String getAccountEditPage() {
        return "admin/account-edit"; 
    }

    @PutMapping("/accounts/update/{id}") // Cần thêm {id}
    @ResponseBody
    public ResponseEntity<String> updateAccount(@PathVariable String id, @RequestBody Account account) {
        // Đảm bảo rằng ID từ URL tương ứng với tài khoản đang được cập nhật
        account.setAccountId(Integer.parseInt(id)); // Gán accountId từ path variable
        boolean updated = accountService.updateAccount(account);
        if (updated) {
            logger.info("Account with ID {} updated successfully.", account.getAccountId());
            return ResponseEntity.ok("Account updated successfully.");
        } else {
            logger.warn("Failed to update account with ID {}.", account.getAccountId());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update account.");
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


}
