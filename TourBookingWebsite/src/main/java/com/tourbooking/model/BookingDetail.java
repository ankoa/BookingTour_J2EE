package com.tourbooking.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
    private int bookingDetailId;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    @JsonBackReference
    private Booking booking;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private Customer customer;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "detail", nullable = false)
    private String detail;

    @Column(name = "status", nullable = false)
    private int status;
}
