package com.tourbooking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tourbooking.model.Account;
import com.tourbooking.repository.AccountRepository;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;


    // Lấy tất cả các tài khoản
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    // Tìm tài khoản theo ID
    public Account getAccountById(String accountId) {
        return accountRepository.findById(Integer.parseInt(accountId)).orElse(null);
    }

    // Thêm tài khoản mới
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    // Cập nhật tài khoản
    public Account updateAccount(String accountId, Account accountDetails) {
        Account account = accountRepository.findById((Integer.parseInt(accountId))).orElse(null);
        if (account != null) {
            account.setAccountName(accountDetails.getAccountName());
            account.setEmail(accountDetails.getEmail());
            account.setStatus(accountDetails.getStatus());
            account.setTime(accountDetails.getTime());
            return accountRepository.save(account);
        }
        return null;
    }

    // Xóa tài khoản
    public void deleteAccount(String accountId) {
        accountRepository.deleteById((Integer.parseInt(accountId)));
    }
}
