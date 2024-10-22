package com.tourbooking.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

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

    @Column(name = "is_percentage")
    private boolean isPercentage;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "status", nullable = false)
    private int status;

    @Column(name = "note")
    private String note;

	@ManyToMany(mappedBy = "discounts")
	@JsonBackReference
	private Set<TourTime> tourTimes;

	public int getDiscountId() {
		return discountId;
	}

	public void setDiscountId(int discountId) {
		this.discountId = discountId;
	}

	public String getDiscountCode() {
		return discountCode;
	}

	public void setDiscountCode(String discountCode) {
		this.discountCode = discountCode;
	}

	public int getDiscountValue() {
		return discountValue;
	}

	public void setDiscountValue(int discountValue) {
		this.discountValue = discountValue;
	}

	public boolean isPercentage() {
		return isPercentage;
	}

	public void setPercentage(boolean isPercentage) {
		this.isPercentage = isPercentage;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}


}

