package com.tourbooking.model;

import jakarta.persistence.*;

	@Entity
	@Table(name = "booking_detail")
	@IdClass(BookingDetailId.class)
	public class BookingDetail {

    @Id
    @Column(name = "booking_id", nullable = false)
    private int bookingId; 

    @Id
    @Column(name = "customer_id", nullable = false)
    private int customerId; 

    @OneToOne
    @JoinColumn(name = "booking_id", insertable = false, updatable = false)
    private Booking booking;

    @OneToOne
    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    private Customer customer;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "status", nullable = false)
    private int status;

    // Getters và Setters
    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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
}
