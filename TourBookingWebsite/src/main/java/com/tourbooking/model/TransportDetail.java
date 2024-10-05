package com.tourbooking.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "transport_detail")
public class TransportDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tour_time_id")
    private int tourTimeId;

    @Column(name = "transport_id", nullable = false)
    private int transportId;

    @Column(name = "direction", nullable = false)
    private String direction;

    @Column(name = "license_plate")
    private String licensePlate;

    @Column(name = "flight_number")
    private String flightNumber;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "status", nullable = false)
    private int status;

	public int getTourTimeId() {
		return tourTimeId;
	}

	public void setTourTimeId(int tourTimeId) {
		this.tourTimeId = tourTimeId;
	}

	public int getTransportId() {
		return transportId;
	}

	public void setTransportId(int transportId) {
		this.transportId = transportId;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

    // Getters and Setters
    
}

