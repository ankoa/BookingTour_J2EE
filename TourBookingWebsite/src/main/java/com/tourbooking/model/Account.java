package com.tourbooking.model;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private int accountId;

    @Column(name = "account_name")
    private String accountName;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS") // Thêm định dạng ở đây
    @Column(name = "time")
    private LocalDateTime time;

    @Column(name = "role")
    private String role;

    private String resetToken;
    private LocalDateTime tokenExpiration;
    @Column(name = "status")
    private int status;

    @OneToOne
    @JoinColumn(name = "customerID", referencedColumnName = "customer_id")
    private Customer customer;
}
