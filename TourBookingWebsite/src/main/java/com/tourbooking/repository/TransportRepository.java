package com.tourbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.tourbooking.model.Transport;

public interface TransportRepository extends JpaRepository<Transport, Integer> {
	@Modifying
    @Transactional
    @Query("UPDATE Transport a SET a.status = 0 WHERE a.transportId = :transportId")
    void deactivateTransport(Integer transportId);
}
