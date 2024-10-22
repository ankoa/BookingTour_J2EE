package com.tourbooking.model;

import java.util.Date;
import jakarta.persistence.*;

@Entity
@Table(name = "tour_time")
public class TourTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tour_time_id")
    private int tourTimeId;

    @ManyToOne
    @JoinColumn(name = "tour_id", nullable = false) 
    private Tour tour;  

    @Column(name = "tour_time_code", nullable = false)
    private String tourTimeCode;

    @Column(name = "time_name", nullable = false)
    private String timeName;

    @Column(name = "departure_time", nullable = false)
    private Date departureTime;

    @Column(name = "return_time", nullable = false)
    private Date returnTime;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "price_adult", nullable = false)
    private int priceAdult;

    @Column(name = "price_child", nullable = false)
    private int priceChild;

    @Column(name = "status", nullable = false)
    private int status;

    // Getters và Setters
    public int getTourTimeId() {
        return tourTimeId;
    }

    public void setTourTimeId(int tourTimeId) {
        this.tourTimeId = tourTimeId;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public String getTourTimeCode() {
        return tourTimeCode;
    }

    public void setTourTimeCode(String tourTimeCode) {
        this.tourTimeCode = tourTimeCode;
    }

    public String getTimeName() {
        return timeName;
    }

    public void setTimeName(String timeName) {
        this.timeName = timeName;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Date getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPriceAdult() {
        return priceAdult;
    }

    public void setPriceAdult(int priceAdult) {
        this.priceAdult = priceAdult;
    }

    public int getPriceChild() {
        return priceChild;
    }

    public void setPriceChild(int priceChild) {
        this.priceChild = priceChild;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
