package com.tourbooking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tourbooking.model.Account;
import com.tourbooking.repository.AccountRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // Lấy tất cả các tài khoản
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Optional<Account> getAccountById(String accountId) {
        return accountRepository.findById(Integer.parseInt(accountId));
    }


    // Thêm tài khoản mới
    public boolean addAccount(Account account) {
        try {
            accountRepository.save(account); // Lưu tài khoản mới
            return true; // Trả về true nếu thêm thành công
        } catch (Exception e) {
            // Ghi log lỗi nếu cần
            return false; // Trả về false nếu có lỗi xảy ra
        }
    }

    // Cập nhật tài khoản
    public boolean updateAccount(Account account) {
        // Tìm tài khoản theo ID
        Account existingAccount = accountRepository.findById(account.getAccountId()).orElse(null);
        if (existingAccount != null) {
            // Cập nhật thông tin tài khoản
            existingAccount.setAccountName(account.getAccountName());
            existingAccount.setEmail(account.getEmail());
            existingAccount.setStatus(account.getStatus());
            accountRepository.save(existingAccount);
            return true;
        }
        return false; // Tài khoản không tồn tại
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
