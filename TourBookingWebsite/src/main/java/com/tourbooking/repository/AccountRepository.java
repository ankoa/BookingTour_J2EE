package com.tourbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tourbooking.model.Account;


public interface AccountRepository extends JpaRepository<Account, Integer> {
}
