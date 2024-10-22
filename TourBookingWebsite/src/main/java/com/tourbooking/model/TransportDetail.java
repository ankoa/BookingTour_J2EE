package com.tourbooking.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "transport_detail")
public class TransportDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transport_detail_id", nullable = false)
    private int transportDetailId;


    @Column(name = "arrival_time")
    private LocalDateTime arrivalTime;

    @Column(name = "departure_time")
    private LocalDateTime departureTime;


    @Column(name = "status", nullable = false)
    private int status;

    @ManyToOne
    @JoinColumn(name = "tour_time_id", nullable = false, referencedColumnName = "tour_time_id")
    @JsonBackReference
    private TourTime tourTime;

    @ManyToOne
    @JoinColumn(name = "transport_id", nullable = false, referencedColumnName = "transport_id")
    private Transport transport;
}

