package com.tourbooking.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private int bookingId;

    @Column(name = "customer_id", nullable = false)
    private int customerId;

    @Column(name = "booking_date", nullable = false)
    private Date bookingDate;

    @Column(name = "total_price", nullable = false)
    private int totalPrice;

    @Column(name = "adult_count", nullable = false)
    private int adultCount;

    @Column(name = "child_count", nullable = false)
    private int childCount;

    @Column(name = "status", nullable = false)
    private int status;

    @Column(name = "time", nullable = false)
    private Date time;

	@ManyToOne
	@JoinColumn(name = "tour_time_id")
	@JsonBackReference
	private TourTime tourTime;

    // Getters and Setters
    
}

