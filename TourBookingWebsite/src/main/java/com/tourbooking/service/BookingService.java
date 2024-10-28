package com.tourbooking.service;

import com.tourbooking.dto.request.BookingRequest;
import com.tourbooking.dto.request.CustomerRequest;
import com.tourbooking.mapper.CustomerMapper;
import com.tourbooking.model.*;
import com.tourbooking.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private TourTimeRepository tourTimeRepository;

    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private BookingDetailRepository bookingDetailRepository;

    // Retrieve all accounts
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    // Find account by ID
    public Account getAccountById(String accountId) {
        return accountRepository.findById(Integer.parseInt(accountId)).orElse(null);
    }

    // Create a new account
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    // Create a new customer
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    // Update account with validation
    public Account updateAccount(String accountId, Account accountDetails) {
        return accountRepository.findById(Integer.parseInt(accountId))
                .map(account -> {
                    if (accountDetails.getAccountName() != null) {
                        account.setAccountName(accountDetails.getAccountName());
                    }
                    if (accountDetails.getEmail() != null) {
                        account.setEmail(accountDetails.getEmail());
                    }
                    if (accountDetails.getStatus() != null) {
                        account.setStatus(accountDetails.getStatus());
                    }
                    if (accountDetails.getTime() != null) {
                        account.setTime(accountDetails.getTime());
                    }
                    return accountRepository.save(account);
                })
                .orElse(null);
    }

    // Delete account by ID
    public void deleteAccount(String accountId) {
        accountRepository.deleteById(Integer.parseInt(accountId));
    }

    // Submit booking form
    public void submitForm(BookingRequest bookingRequest) {
        Date currentDate = new Date();
        List<Customer> customers = createCustomerList(bookingRequest, currentDate);
        Customer customerRepresentative = customers.get(0);  // assuming the first is the representative

        TourTime tourTime = tourTimeRepository.findById(bookingRequest.getTourTimeId()).orElse(null);
        Booking newBooking = createBooking(bookingRequest, currentDate, customerRepresentative, tourTime);
        
        bookingRepository.save(newBooking);
        saveBookingDetails(customers, newBooking, tourTime);
    }

    private List<Customer> createCustomerList(BookingRequest bookingRequest, Date currentDate) {
        List<Customer> customers = new ArrayList<>();

        // Representative Customer
        Customer customerRepresentative = customerMapper.toCustomer(new CustomerRequest(
                bookingRequest.getName(), 0, bookingRequest.getPhoneNumber(), null, bookingRequest.getAddress()));
        customerRepresentative.setCustomerType(1);
        customerRepresentative.setTime(currentDate);
        customerRepository.save(customerRepresentative);
        customers.add(customerRepresentative);

        // Adult Customers
        bookingRequest.getAdults().forEach(customerRequest -> {
            Customer customer = customerMapper.toCustomer(customerRequest);
            customer.setCustomer(customerRepresentative);
            customer.setTime(currentDate);
            customer.setCustomerType(1);
            customerRepository.save(customer);
            customers.add(customer);
        });

        // Child Customers
        bookingRequest.getChildren().forEach(customerRequest -> {
            Customer customer = customerMapper.toCustomer(customerRequest);
            customer.setCustomer(customerRepresentative);
            customer.setTime(currentDate);
            customer.setCustomerType(2);
            customerRepository.save(customer);
            customers.add(customer);
        });

        return customers;
    }

    private Booking createBooking(BookingRequest bookingRequest, Date currentDate, Customer customerRepresentative, TourTime tourTime) {
        Booking newBooking = new Booking();
        newBooking.setCustomer(customerRepresentative);
        newBooking.setAdultCount(bookingRequest.getAdults().size());
        newBooking.setChildCount(bookingRequest.getChildren().size());
        newBooking.setStatus(1);
        newBooking.setTime(currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        newBooking.setTourTime(tourTime);

        Discount discount = null;
        if (bookingRequest.getVoucherCode() != null) {
            discount = discountRepository.findByDiscountCode(bookingRequest.getVoucherCode());
        }

        if (tourTime != null && discount != null) {
            newBooking.setTotalPrice(
                    tourTime.getPriceAdult() * bookingRequest.getAdults().size() +
                    tourTime.getPriceChild() * bookingRequest.getChildren().size() -
                    discount.getDiscountValue()
            );
        } else if (tourTime != null) {
            newBooking.setTotalPrice(
                    tourTime.getPriceAdult() * bookingRequest.getAdults().size() +
                    tourTime.getPriceChild() * bookingRequest.getChildren().size()
            );
        }
        return newBooking;
    }

    private void saveBookingDetails(List<Customer> customers, Booking booking, TourTime tourTime) {
        for (Customer customer : customers) {
            BookingDetail bookingDetail = new BookingDetail();
            bookingDetail.setCustomer(customer);
            bookingDetail.setBooking(booking);
            bookingDetail.setPrice(customer.getCustomerType() == 1 ? tourTime.getPriceAdult() : tourTime.getPriceChild());
            bookingDetail.setStatus(1);
            bookingDetailRepository.save(bookingDetail);
        }
    }
}
