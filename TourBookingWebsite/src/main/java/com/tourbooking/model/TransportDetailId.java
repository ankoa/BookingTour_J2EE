package com.tourbooking.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TransportDetailId implements Serializable {
    private int transportId;
    private int tourTimeId;

    public TransportDetailId() {}

    public TransportDetailId(int transportId, int tourTimeId) {
        this.transportId = transportId;
        this.tourTimeId = tourTimeId;
    }

    // Getters and Setters

    public int getTransportId() {
        return transportId;
    }

    public void setTransportId(int transportId) {
        this.transportId = transportId;
    }

    public int getTourTimeId() {
        return tourTimeId;
    }

    public void setTourTimeId(int tourTimeId) {
        this.tourTimeId = tourTimeId;
    }

    // Override equals() and hashCode() để đảm bảo tính duy nhất của khóa chính
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransportDetailId)) return false;
        TransportDetailId that = (TransportDetailId) o;
        return transportId == that.transportId && tourTimeId == that.tourTimeId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(transportId, tourTimeId);
    }
}
