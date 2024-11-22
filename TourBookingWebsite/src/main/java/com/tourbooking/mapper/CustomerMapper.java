package com.tourbooking.mapper;

import com.tourbooking.dto.request.CustomerRequest;
import com.tourbooking.dto.response.CustomerResponse;
import com.tourbooking.model.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerRequest toCustomerRequest(Customer customer);

    CustomerResponse toCustomerResponse(Customer customer);

    Customer toCustomer(CustomerRequest customerRequest);

    Customer toCustomer(CustomerResponse customerResponse);
}
