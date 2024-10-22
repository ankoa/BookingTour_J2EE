package com.tourbooking.model;

import java.io.Serializable;
import java.util.Objects;

public class TourDiscountId implements Serializable {
    private int tourTimeId;
    private int discountId;

    // Getters and Setters
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TourDiscountId)) return false;
        TourDiscountId that = (TourDiscountId) o;
        return tourTimeId == that.tourTimeId && discountId == that.discountId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tourTimeId, discountId);
    }
}
