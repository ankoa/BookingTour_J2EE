package com.tourbooking.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tourbooking.model.Customer;


public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}