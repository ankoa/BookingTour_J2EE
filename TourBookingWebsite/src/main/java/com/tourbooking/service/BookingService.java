package com.tourbooking.service;

import com.tourbooking.dto.request.BookingRequest;
import com.tourbooking.dto.request.CustomerRequest;
import com.tourbooking.mapper.CustomerMapper;
import com.tourbooking.model.*;
import com.tourbooking.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    private TourTimeService tourTimeService;

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
    public Account getAccountById(Integer accountId) {
        return accountRepository.findById(accountId).orElse(null);
    }
    public Account getAccountById(String accountId) {
        return getAccountById(Integer.parseInt(accountId));
    }

    // Thêm tài khoản mới
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    // Cập nhật tài khoản
    public Account updateAccount(String accountId, Account accountDetails) {
        return accountRepository.findById(Integer.parseInt(accountId))
                .map(account -> {
                    // Kiểm tra dữ liệu hợp lệ trước khi cập nhật
                    if (accountDetails.getAccountName() != null) {
                        account.setAccountName(accountDetails.getAccountName());
                    }
                    if (accountDetails.getEmail() != null) {
                        account.setEmail(accountDetails.getEmail());
                    }
                    account.setStatus(accountDetails.getStatus());
                    account.setTime(accountDetails.getTime());
                    return accountRepository.save(account);
                })
                .orElse(null);
    }

    // Xóa tài khoản
    public void deleteAccount(String accountId) {
        accountRepository.deleteById(Integer.parseInt(accountId));
    }

    public boolean submitForm(BookingRequest bookingRequest, Integer status) {
        // lay tour time da dat
        TourTime tourTime = tourTimeService.findById(bookingRequest.getTourTimeId(),status)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy tour time với ID: " + bookingRequest.getTourTimeId()));

        if (status != null && tourTime.getStatus() != status) return false;

        int voucherValue=0;
        if (bookingRequest.getVoucherCode() != null) {
            Discount discount = discountRepository.findByDiscountCode(bookingRequest.getVoucherCode());
            if (discount != null&& discount.getStatus()==1)
                voucherValue=discount.getDiscountValue();
        }

            //check remainPax
        if (tourTimeService.calculateRemainPax(tourTime) <
                (bookingRequest.getAdults().size() + bookingRequest.getChildren().size())) return false;

        Date currentDate = new Date();

        //danh sach khach hang vua book
        List<Customer> customers = new ArrayList<>();

        //kiem tra va luu nguoi dai dien
        Customer customerRelationship;
        if (bookingRequest.getAccountId() != 0) {
            Account account = getAccountById(bookingRequest.getAccountId());
            if(status !=null && account.getStatus() != status){return false;}
            if(status !=null && account.getCustomer().getStatus() != status){return false;}
            customerRelationship = account.getCustomer();
        } else {
            CustomerRequest customerRequest = new CustomerRequest();
            customerRelationship = customerMapper.toCustomer(customerRequest);
            customerRelationship.setCustomerType(1);
            customerRelationship.setTime(currentDate);
            customerRelationship.setStatus(1);
            customerRepository.save(customerRelationship);
        }

        // luu danh sach nguoi lon
        bookingRequest.getAdults().forEach(customerRequest -> {
            Customer customer = customerMapper.toCustomer(customerRequest);
            customer.setRelatedCustomer(customerRelationship);
            customer.setTime(currentDate);
            customer.setCustomerType(1);
            customerRepository.save(customer);
            customers.add(customer);
        });

        // luu danh sach tre em
        bookingRequest.getChildren().forEach(customerRequest -> {
            Customer customer = customerMapper.toCustomer(customerRequest);
            customer.setRelatedCustomer(customerRelationship);
            customer.setTime(currentDate);
            customer.setCustomerType(2);
            customerRepository.save(customer);
            customers.add(customer);
        });


        //gia thuong
        int totalPrice = tourTime.getPriceAdult() * bookingRequest.getAdults().size() +
                tourTime.getPriceChild() * bookingRequest.getChildren().size();

        int discountValue = 0;

        //gia co discount
        if (!tourTime.getDiscounts().isEmpty()) {
            for (Discount discount : tourTime.getDiscounts()) {
                if (discount.getStartDate() != null)
                    if (!currentDate.after(discount.getStartDate())) continue;
                if (discount.getEndDate() != null)
                    if (!currentDate.before(discount.getEndDate())) continue;
                discountValue = (tourTime.getPriceAdult() - discount.getDiscountValue()) * bookingRequest.getChildren().size() +
                        (tourTime.getPriceChild() - discount.getDiscountValue()) * bookingRequest.getAdults().size();
                break;
            }
        }

        //luu du lie booking
        Booking newBooking = new Booking();
        newBooking.setCustomer(customerRelationship);
        newBooking.setAdultCount(bookingRequest.getAdults().size());
        newBooking.setChildCount(bookingRequest.getChildren().size());
        newBooking.setStatus(1);
        newBooking.setTime(currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        newBooking.setTourTime(tourTime);
        newBooking.setTotalPrice(totalPrice);
        newBooking.setVoucherPrice(discountValue+voucherValue);


        bookingRepository.save(newBooking);


        for (Customer customer : customers) {
            BookingDetail bookingDetail = new BookingDetail();
            bookingDetail.setCustomer(customer);
            bookingDetail.setBooking(newBooking);
            bookingDetail.setPrice(customer.getCustomerType() == 1 ? tourTime.getPriceAdult() : tourTime.getPriceChild());
            bookingDetail.setStatus(1);
            bookingDetailRepository.save(bookingDetail);
        }

        return true;
    }

    public List<Booking> getBookingWithPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "time"));
        Page<Booking> listBooking = bookingRepository.findAll(pageable);
        return listBooking.getContent();
    }
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll(); // Lấy tất cả booking từ repository
    }
    public boolean deactivateBooking(Integer id) {
        int updatedCount = bookingRepository.deactivateBooking(id);
        return updatedCount > 0;
    }
    public boolean addBooking(Booking booking) {
        try {
            // Lưu đối tượng Booking vào cơ sở dữ liệu
            bookingRepository.save(booking);
            // Nếu lưu thành công, trả về true
            return true;
        } catch (Exception e) {
            // Nếu có lỗi xảy ra (ví dụ: ngoại lệ khi lưu vào cơ sở dữ liệu), trả về false
            return false;
        }
    }
    public Optional<Booking> getBookingById(Integer bookingId) {
        return bookingRepository.findById(bookingId);
    }



}