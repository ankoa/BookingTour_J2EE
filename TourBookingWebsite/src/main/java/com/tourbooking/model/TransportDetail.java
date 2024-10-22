package com.tourbooking.model;

import jakarta.persistence.*;

@Entity
@Table(name = "transport_detail")
@IdClass(TransportDetailId.class)
public class TransportDetail {

    @Id
    @Column(name = "transport_id", nullable = false)
    private int transportId;

    @Id
    @Column(name = "tour_time_id", nullable = false)
    private int tourTimeId;

    @OneToOne
    @JoinColumn(name = "transport_id", insertable = false, updatable = false)
    private Transport transport;

    @OneToOne
    @JoinColumn(name = "tour_time_id", insertable = false, updatable = false)
    private TourTime tourTime;

    @Column(name = "direction", nullable = false)
    private String direction;

    // Getters và Setters
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

    public Transport getTransport() {
        return transport;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    public TourTime getTourTime() {
        return tourTime;
    }

    public void setTourTime(TourTime tourTime) {
        this.tourTime = tourTime;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
