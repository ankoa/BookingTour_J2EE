package com.tourbooking.model;

import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name = "tour_time")
public class TourTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tour_time_id")
    private int tourTimeId;

    @Column(name = "tour_id", nullable = false)
    private int tourId;

    @Column(name = "tour_time_code", nullable = false)
    private String tourTimeCode;

    @Column(name = "time_name", nullable = false)
    private String timeName;

    @Column(name = "departure_time", nullable = false)
    private Date departureTime;

    @Column(name = "return_time", nullable = false)
    private Date returnTime;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "price_adult", nullable = false)
    private int priceAdult;

    @Column(name = "price_child", nullable = false)
    private int priceChild;

    @Column(name = "status", nullable = false)
    private int status;

    // Getters and Setters
}
