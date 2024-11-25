package com.tourbooking.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private int categoryId; 

    @Column(name = "category_name", nullable = false)
    private String categoryName; 

    @Column(name = "category_detail",columnDefinition = "TEXT")
    private String categoryDetail; 

    @Column(name = "status", nullable = false)
    private int status; 
    @JsonIgnore
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<Tour> tours;

}
