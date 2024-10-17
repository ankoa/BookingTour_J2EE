package com.tourbooking.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tour")
public class Tour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tourId;

    @Column(name = "category_id")
    private int categoryId;

    @Column(name = "tour_name")
    private String tourName;

    @Column(name = "tour_detail")
    private String tourDetail;

    @Column(name = "url")
    private String url; 

    @Column(name = "status")
    private int status; 

    @Column(name = "tour_code")
    private String tourCode; 

    public Tour() {}

    // Getter và Setter
    public int getTourId() {
        return tourId;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }

    public int getCategoryId() { 
        return categoryId;
    }

    public void setCategoryId(int categoryId) { 
        this.categoryId = categoryId;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public String getTourDetail() {
        return tourDetail; 
    }

    public void setTourDetail(String tourDetail) {
        this.tourDetail = tourDetail; 
    }

    public String getUrl() {
        return url; 
    }

    public void setUrl(String url) {
        this.url = url; 
    }

    public int getStatus() {
        return status; 
    }

    public void setStatus(int status) {
        this.status = status; 
    }

    public String getTourCode() {
        return tourCode; 
    }

    public void setTourCode(String tourCode) {
        this.tourCode = tourCode; 
    }
}
