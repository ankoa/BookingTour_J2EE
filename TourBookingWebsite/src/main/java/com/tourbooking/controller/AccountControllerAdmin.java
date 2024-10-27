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
        System.out.println("Total accounts: " + accounts.size());
        model.addAttribute("accounts", accounts);
        return "account-all"; 
    }
 // Phương thức hiển thị form chỉnh sửa tài khoản
    @GetMapping("/account-edit/{id}")
    public String showEditForm(@PathVariable("id") String id, Model model) {
        Account account = accountService.getAccountById(id);
        model.addAttribute("account", account);
        return "account-edit"; 
    }


    @PostMapping("/account-edit/{id}")
    public String updateAccount(@PathVariable("id") String id, @ModelAttribute("account") Account account) {
        accountService.updateAccount(id, account);
        return "redirect:/admin/accounts"; 
    }
    @PostMapping("/account-delete/{id}")
    public String deleteAccount(@PathVariable("id") String id) {
        accountService.deleteAccount(id); 
        return "redirect:/admin/accounts"; 
    }



}

