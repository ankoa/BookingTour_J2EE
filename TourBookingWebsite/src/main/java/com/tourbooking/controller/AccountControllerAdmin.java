package com.tourbooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

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

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AccountControllerAdmin {

    private final AccountService accountService;

    @Autowired
    public AccountControllerAdmin(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/accounts")
    public String getAllAccounts(Model model) {
        List<Account> accounts = accountService.getAllAccounts();
        System.out.println("Total accounts: " + accounts.size()); // In ra số lượng tài khoản
        model.addAttribute("accounts", accounts);
        return "account-all"; 
    }

}

