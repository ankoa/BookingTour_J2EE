package com.tourbooking.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tour")
public class Tour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tour_id")
    private int tourId;

    @Column(name = "tour_name")
    private String tourName;

    @Column(name = "tour_detail",columnDefinition = "TEXT")
    private String tourDetail;

    @Column(name = "status")
    private int status;

    @Column(name = "tour_code")
    private String tourCode;

    @Column(name = "tour_stay")
    private String dayStay;

    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<TourTime> tourTimes;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<TourImage> tourImages;


}
