package com.tourbooking.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "transport")
public class Transport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transport_id")
    private int transportId;

    @Column(name = "transport_name", nullable = false)
    private String transportName;

    @Column(name = "transport_detail", nullable = false)
    private String transportDetail;

    @Column(name = "departure_time", nullable = false)
    private int departureTime;

    @Column(name = "departure_location", nullable = false)
    private String departureLocation;

    @Column(name = "destination_location", nullable = false)
    private String destinationLocation;

    @Column(name = "status", nullable = false)
    private int status;

    // Getters and Setters
}
