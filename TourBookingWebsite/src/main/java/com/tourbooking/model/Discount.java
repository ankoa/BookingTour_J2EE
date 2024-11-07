package com.tourbooking.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "discount")
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "discount_id")
    private int discountId;

    @Column(name = "discount_code", nullable = false)
    private String discountCode;

    @Column(name = "discount_value", nullable = false)
    private int discountValue;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "status", nullable = false)
    private int status;

    @Column(name = "note",columnDefinition = "TEXT")
    private String note;

	@ManyToMany(mappedBy = "discounts")
	@JsonBackReference
	private Set<TourTime> tourTimes;
}

