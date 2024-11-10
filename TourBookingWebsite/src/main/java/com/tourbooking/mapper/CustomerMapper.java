package com.tourbooking.mapper;

import com.tourbooking.dto.request.CustomerRequest;
import com.tourbooking.model.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerRequest toCustomerRequest(Customer customer);

    Customer toCustomer(CustomerRequest CustomerRequest);
}
