package com.tourbooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tourbooking.model.Account;
import com.tourbooking.repository.AccountRepository;
import com.tourbooking.service.AccountService;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.tourbooking.model.Account;
import com.tourbooking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // Lấy danh sách tài khoản và render vào trang HTML
    @GetMapping("/accounts")
    public String getAllAccounts(Model model) {
        List<Account> accounts = accountService.getAllAccounts();
        model.addAttribute("accounts", accounts);
        return "account-list"; // Tên trang HTML (account-list.html)
    }
}

