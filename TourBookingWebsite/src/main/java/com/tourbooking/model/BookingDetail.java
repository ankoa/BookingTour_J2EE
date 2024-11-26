package com.tourbooking.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "booking_detail")
public class BookingDetail {

    @Id
    @Column(name = "booking_detail_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingDetailId;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    @JsonManagedReference
    private Booking booking;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonManagedReference
    private Customer customer;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "detail",columnDefinition = "TEXT")
    private String detail;

    @Column(name = "status", nullable = false)
    private int status;
}
