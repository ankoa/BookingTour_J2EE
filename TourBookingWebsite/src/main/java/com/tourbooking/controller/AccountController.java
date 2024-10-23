package com.tourbooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.tourbooking.model.Account;
import com.tourbooking.repository.AccountRepository;
import com.tourbooking.service.AccountService;

import java.util.List;

import org.springframework.stereotype.Controller;

@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    // Lấy danh sách tài khoản và render vào trang HTML
    @GetMapping("/accounts")
    public String getAllAccounts(Model model) {
        List<Account> accounts = accountService.getAllAccounts();
        model.addAttribute("accounts", accounts);
        return "account-list"; // Tên trang HTML (account-list.html)
    }
}
