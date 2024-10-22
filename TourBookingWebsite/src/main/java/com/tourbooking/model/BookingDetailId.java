package com.tourbooking.model;

import java.io.Serializable;
import java.util.Objects;

public class BookingDetailId implements Serializable {
    private int bookingId;
    private int customerId;

    // Constructor, equals, and hashCode methods

    public BookingDetailId() {}

    public BookingDetailId(int bookingId, int customerId) {
        this.bookingId = bookingId;
        this.customerId = customerId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public int getCustomerId() {
        return customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookingDetailId)) return false;
        BookingDetailId that = (BookingDetailId) o;
        return bookingId == that.bookingId && customerId == that.customerId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingId, customerId);
    }
}
