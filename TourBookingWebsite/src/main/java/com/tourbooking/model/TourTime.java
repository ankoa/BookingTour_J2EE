package com.tourbooking.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tour_time")
public class TourTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tour_time_id")
    private int tourTimeId;

    @Column(name = "tour_time_code", nullable = false)
    private String tourTimeCode;

    @Column(name = "time_name", nullable = false)
    private String timeName;

    @Column(name = "departure_time", nullable = false)
    private LocalDateTime departureTime;

    @Column(name = "return_time", nullable = false)
    private LocalDateTime returnTime;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "price_adult", nullable = false)
    private int priceAdult;

    @Column(name = "price_child", nullable = false)
    private int priceChild;

    @Column(name="note", nullable = false)
    private String note;

    @Column(name = "status", nullable = false)
    private int status;

    @ManyToOne
    @JoinColumn(name = "tour_id")
    @JsonBackReference
    private Tour tour;

    @OneToMany(mappedBy = "tourTime", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<TransportDetail> transportDetails;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "tour_discount",
            joinColumns = @JoinColumn(name = "tour_time_id"),
            inverseJoinColumns = @JoinColumn(name = "discount_id")
    )
    @JsonManagedReference
    private Set<Discount> discounts;

    @OneToMany(mappedBy = "tourTime", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<Booking> bookings;

    // Getters and Setters

}
