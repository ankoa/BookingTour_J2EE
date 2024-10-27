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
    CustomerMapper customerMapper;

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private TourTimeRepository tourTimeRepository;

    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private BookingDetailRepository bookingDetailRepository;

    public BookingService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // Lấy tất cả các tài khoản
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    // Tìm tài khoản theo ID
    public Account getAccountById(String accountId) {
        return accountRepository.findById((Integer.parseInt(accountId))).orElse(null);
    }

    // Thêm tài khoản mới
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    // Cập nhật tài khoản
    public Account upcurrentDateAccount(String accountId, Account accountDetails) {
        Account account = accountRepository.findById((Integer.parseInt(accountId))).orElse(null);
        if (account != null) {
            account.setAccountName(accountDetails.getAccountName());
            account.setEmail(accountDetails.getEmail());
            account.setStatus(accountDetails.getStatus());
            account.setTime(accountDetails.getTime());
            return accountRepository.save(account);
        }
        return null;
    }

    // Xóa tài khoản
    public void deleteAccount(String accountId) {
        accountRepository.deleteById((Integer.parseInt(accountId)));
    }

    public void submitForm(BookingRequest bookingRequest) {
        //thoi gian hien tai
        Date currentDate = new Date();

        //danh sach khach hang vua book
        List<Customer> customers = new ArrayList<>();

        // luu nguoi dai dien
        Customer customerRelative = customerMapper.toCustomer(
                new CustomerRequest(bookingRequest.getName(), 0, bookingRequest.getPhoneNumber(), null, bookingRequest.getAddress())
        );
        customerRelative.setCustomerType(1);
        customerRelative.setTime(currentDate);
        customerRelative.setPhoneNumber(bookingRequest.getPhoneNumber());
        customerRepository.save(customerRelative);
        customers.add(customerRelative);

        // luu danh sach nguoi lon
        bookingRequest.getAdults().forEach(customerRequest -> {
            Customer customer = customerMapper.toCustomer(customerRequest);
            customer.setCustomer(customerRelative);
            customer.setTime(currentDate);
            customer.setCustomerType(1);
            customerRepository.save(customer);
            customers.add(customer);
        });

        // luu danh sach tre em
        bookingRequest.getChildren().forEach(customerRequest -> {
            Customer customer = customerMapper.toCustomer(customerRequest);
            customer.setCustomer(customerRelative);
            customer.setTime(currentDate);
            customer.setCustomerType(2);
            customerRepository.save(customer);
            customers.add(customer);
        });

        // lay tour time da dat
        TourTime tourTime = tourTimeRepository.findById(bookingRequest.getTourTimeId()).orElse(null);

        //luu du lie booking
        Booking newBooking = new Booking();
        newBooking.setCustomer(customerRelative);
        newBooking.setAdultCount(bookingRequest.getAdults().size());
        newBooking.setChildCount(bookingRequest.getChildren().size());
        newBooking.setStatus(1);
        newBooking.setTime(currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        newBooking.setTourTime(tourTime);

        //co ma giam hop le thi gia vao tong cong
        Discount discount = null;
        if (bookingRequest.getVoucherCode() != null) {
            discount = discountRepository.findByDiscountCode(bookingRequest.getVoucherCode());
        }
        if (tourTime != null && discount != null)
            newBooking.setTotalPrice(tourTime.getPriceAdult() * bookingRequest.getChildren().size() +
                    tourTime.getPriceChild() * bookingRequest.getAdults().size() - discount.getDiscountValue());
        else newBooking.setTotalPrice(tourTime.getPriceAdult() * bookingRequest.getChildren().size() +
                tourTime.getPriceChild() * bookingRequest.getAdults().size());
        bookingRepository.save(newBooking);


        for (Customer customer : customers) {
            BookingDetail bookingDetail = new BookingDetail();
            bookingDetail.setCustomer(customer);
            bookingDetail.setBooking(newBooking);
            bookingDetail.setPrice(customer.getCustomerType() == 1 ? tourTime.getPriceAdult() : tourTime.getPriceChild());
            bookingDetail.setStatus(1);
            bookingDetailRepository.save(bookingDetail);
        }
    }
}
