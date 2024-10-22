package com.tourbooking.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "transport")
public class Transport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transport_id")
    private int transportId;

    @Column(name = "transport_name", nullable = false)
    private String transportName;

    @Column(name = "transport_code", nullable = false)
    private String transportCode;

    @Column(name = "departure_location", nullable = false)
    private String departureLocation;

    @Column(name = "destination_location", nullable = false)
    private String destinationLocation;

    @Column(name = "status", nullable = false)
    private int status;

    @JsonManagedReference
    @OneToMany(mappedBy = "transport", cascade = CascadeType.ALL)
    private Set<TransportDetail> transportDetails;

}
