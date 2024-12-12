package com.tourbooking.controller.client;

import com.tourbooking.dto.response.BookingResponse;
import com.tourbooking.security.CustomUserDetails;
import com.tourbooking.service.BookingService;
import com.tourbooking.service.CategoryService;
import com.tourbooking.service.TourService;
import com.tourbooking.service.TourTimeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private TourService tourService;

    @Autowired
    private TourTimeService tourTimeService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BookingService bookingService;

    @GetMapping({"/", ""})
    public String index() {
        return "client/index"; // Trả về tên template mà không cần phần mở rộng
    }

    @GetMapping("/sample")
    public String getDemoSampleClient(Model model) {
        return "client/sample";
    }

    @GetMapping("/admin")
    public String getHomepage(Model model) {
        return "admin/homepage";
    }

    @GetMapping("/home")
    public String getHome(Model model) {
        return "index";
    }

    @GetMapping("/account-login")
    public String getLogin(Model model, @AuthenticationPrincipal CustomUserDetails user) {
        if (user != null) {
            return "redirect:/login-success";
        }
        return "client/login";
    }

    @RequestMapping("/login-success")
    public String afterLoginSuccess(HttpServletRequest request) {
        // if (request.isUserInRole("ROLE_ADMIN")) {
        // return "redirect:/admin/tour-all";
        // }
        return "redirect:/find-tour";
    }

    @GetMapping("/register")

    public String getRegister(Model model) {
        return "client/register";
    }

    @GetMapping("/find-tour")
    public String getFindTour(Model model, @AuthenticationPrincipal CustomUserDetails user) {
        model.addAttribute("minDate", LocalDate.now());
        model.addAttribute("categories", categoryService.getCategories());
        return "client/find-tour";
    }





    @GetMapping("/payment-failure")
    public String paymentFail() {
        return "client/payment-fail.html";
    }

    @GetMapping("/account-info")
    public String getMyInfo(Model model,
                            @AuthenticationPrincipal CustomUserDetails user) {
        if (user == null) return "redirect:/account-login";
        List<BookingResponse> bookingResponses = bookingService.getBookingResponses(user.getAccount(), null, 0, 5);
        int getNumberOfPages= bookingService.getNumberOfPages(user.getAccount(),5);
        model.addAttribute("user", user);
        model.addAttribute("totalPage", getNumberOfPages);
        model.addAttribute("bookingResponses", bookingResponses);

        return "client/account-info";
    }
}