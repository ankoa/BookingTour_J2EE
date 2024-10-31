package com.tourbooking.model;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private int customerId;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "sex")
    private int sex;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "time")
    private Date time;

    @Column(name = "status")
    private int status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    @JsonManagedReference
    private Account account;

    // Quan hệ nhiều khách hàng có thể liên kết tới một khách hàng liên quan
    @ManyToOne
    @JoinColumn(name ="customer_rel_id", referencedColumnName = "customer_id")
    @JsonBackReference
    private Customer relatedCustomer;

    // Một khách hàng có thể liên kết tới nhiều khách hàng
    @OneToMany(mappedBy = "relatedCustomer", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Customer> customers;

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Booking> bookings;

    @Column(name="customer_type")
    private int customerType;
}
