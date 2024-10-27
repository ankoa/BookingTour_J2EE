package com.tourbooking.service;

import com.tourbooking.model.Account;
import com.tourbooking.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private AccountRepository accountRepository;

    public BookingService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

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
        return accountRepository.findById(Integer.parseInt(accountId))
                .map(account -> {
                    // Kiểm tra dữ liệu hợp lệ trước khi cập nhật
                    if (accountDetails.getAccountName() != null) {
                        account.setAccountName(accountDetails.getAccountName());
                    }
                    if (accountDetails.getEmail() != null) {
                        account.setEmail(accountDetails.getEmail());
                    }
                    account.setStatus(accountDetails.getStatus());
                    account.setTime(accountDetails.getTime());
                    return accountRepository.save(account);
                })
                .orElse(null);
    }

    // Xóa tài khoản
    public void deleteAccount(String accountId) {
        accountRepository.deleteById(Integer.parseInt(accountId));
    }
}
