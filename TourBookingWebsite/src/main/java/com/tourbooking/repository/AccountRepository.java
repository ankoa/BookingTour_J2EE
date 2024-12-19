package com.tourbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.tourbooking.model.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE Account a SET a.status = 0 WHERE a.accountId = :accountId")
    void deactivateAccount(Integer accountId);

    @Query("SELECT COUNT(a) > 0 FROM Account a WHERE a.customer.customerId = :customerID")
    boolean checkCustomerIDExists(Integer customerID);

     Account findByAccountName(String accountName);
     Account findByEmail(String email);

     boolean existsByEmail(String email);
     boolean existsByAccountName(String accountName);

     Account findByResetToken(String resetToken);
}
