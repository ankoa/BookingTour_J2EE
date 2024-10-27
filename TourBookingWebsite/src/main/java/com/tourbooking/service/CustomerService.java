package com.tourbooking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tourbooking.model.Customer;
import com.tourbooking.repository.CustomerRepository;

import java.sql.Date;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    // Lấy tất cả khách hàng
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Tìm khách hàng theo ID
    public Customer getCustomerById(int customerId) {
        return customerRepository.findById(customerId).orElse(null);
    }

    // Cập nhật khách hàng
    public Customer updateCustomer(int customerId, Customer customerDetails) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer != null) {
            customer.setCustomerName(customerDetails.getCustomerName());
            customer.setPhoneNumber(customerDetails.getPhoneNumber());
            customer.setEmail(customerDetails.getEmail());
            customer.setAddress(customerDetails.getAddress());
            //customer.setBirthday(customerDetails.getBirthday());
            customer.setStatus(customerDetails.getStatus());
            return customerRepository.save(customer);
        }
        return null;
    }

    // Xóa khách hàng
    public void deleteCustomer(int customerId) {
        customerRepository.deleteById(customerId);
    }
}
