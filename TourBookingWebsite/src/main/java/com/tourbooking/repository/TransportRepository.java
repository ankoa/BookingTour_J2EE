package com.tourbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tourbooking.model.Transport;

public interface TransportRepository extends JpaRepository<Transport, Integer> {
}
