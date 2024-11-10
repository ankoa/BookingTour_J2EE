package com.tourbooking.model;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private int bookingId;

    @Column(name = "total_price", nullable = false)
    private int totalPrice;

    @Column(name = "adult_count", nullable = false)
    private int adultCount;

    @Column(name = "child_count", nullable = false)
    private int childCount;

    @Column(name = "status", nullable = false)
    private int status;

    @Column(name = "time", nullable = false)
    private LocalDateTime time;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "tour_time_id", nullable = false)
    private TourTime tourTime;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "customer_id", nullable = false, referencedColumnName = "customer_id")
    private Customer customer;

    @Column(name="total_discount")
    private Integer totalDiscount;
}
