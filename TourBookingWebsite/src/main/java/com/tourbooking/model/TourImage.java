package com.tourbooking.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tour_image")
public class TourImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private int imageId;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "tour_id")
    private Tour tour;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "status")
    private int status;
}
