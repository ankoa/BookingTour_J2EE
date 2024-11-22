package com.tourbooking.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {
    private CustomerRequest relatedCustomer;
    private String voucherCode;
    private String note;
    private Set<CustomerRequest> adults;
    private Set<CustomerRequest> children;
    private Integer tourTimeId;
    private Integer accountId;
    private String paymentMethod;
    private String bankCode;
}
