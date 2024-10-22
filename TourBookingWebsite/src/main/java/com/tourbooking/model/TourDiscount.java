package com.tourbooking.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tour_discount")
@IdClass(TourDiscountId.class)
public class TourDiscount {

    @Id
    @Column(name = "tour_time_id", nullable = false)
    private int tourTimeId;

    @Id
    @Column(name = "discount_id", nullable = false)
    private int discountId;

    @OneToOne
    @JoinColumn(name = "tour_time_id", insertable = false, updatable = false)
    private TourTime tourTime;

    @OneToOne
    @JoinColumn(name = "discount_id", insertable = false, updatable = false)
    private Discount discount;

    // Getters và Setters
    public int getTourTimeId() {
        return tourTimeId;
    }

    public void setTourTimeId(int tourTimeId) {
        this.tourTimeId = tourTimeId;
    }

    public int getDiscountId() {
        return discountId;
    }

    public void setDiscountId(int discountId) {
        this.discountId = discountId;
    }

    public TourTime getTourTime() {
        return tourTime;
    }

    public void setTourTime(TourTime tourTime) {
        this.tourTime = tourTime;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }
}
