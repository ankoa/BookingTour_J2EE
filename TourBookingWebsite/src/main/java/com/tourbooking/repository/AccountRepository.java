package com.tourbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.tourbooking.model.Account;


public interface AccountRepository extends JpaRepository<Account, Integer> {
	
	@Modifying
    @Transactional
    @Query("UPDATE Account a SET a.status = 0 WHERE a.id = :accountId")
    void deactivateAccount(Integer accountId);
}
