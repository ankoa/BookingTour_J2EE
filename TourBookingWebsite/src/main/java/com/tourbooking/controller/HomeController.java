package com.tourbooking.controller;

import com.tourbooking.dto.response.BookingResponse;
import com.tourbooking.dto.response.TourImageResponse;
import com.tourbooking.dto.response.TourResponse;
import com.tourbooking.dto.response.TourTimeResponse;
import com.tourbooking.security.CustomUserDetails;
import com.tourbooking.service.BookingService;
import com.tourbooking.service.TourService;
import com.tourbooking.service.TourTimeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private TourService tourService;

    @Autowired
    private TourTimeService tourTimeService;

    @Autowired
    private BookingService bookingService;

    @GetMapping({"/",""})
    public String index() {
        return "client/index"; // Trả về tên template mà không cần phần mở rộng
    }

    @GetMapping("/sample")
    public String getDemoSampleClient(Model model) {
        return "client/sample";
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
        model.addAttribute("user", user);

        return "client/find-tour";
    }

    @GetMapping("/tour/{id}")
    public String getTour(@PathVariable("id") String id,
                          Model model,
                          @AuthenticationPrincipal CustomUserDetails user) {
        TourResponse tourResponse =  tourService.getTourResponse(id,1);

        if(tourResponse==null)
            return "redirect:/";

        //add image default
        if (tourResponse.getTourImages().isEmpty()) {
            List<TourImageResponse> listTourImage = new ArrayList<>();
            listTourImage.add(new TourImageResponse(999,"/client/img/54.jpg",1));
            listTourImage.add(new TourImageResponse(2,"/client/img/55.jpg",2));
            tourResponse.setTourImages(listTourImage);
        }

        model.addAttribute("user", user);
        model.addAttribute("tourResponse", tourResponse);
        return "client/tour-detail";
    }

    @GetMapping("/order-booking")
    public String getOrderBooking(Model model,
            @RequestParam(required = true) String tourTimeId,
            @AuthenticationPrincipal CustomUserDetails user) {
        TourTimeResponse tourTimeResponse = tourTimeService.getTourTimeResponseById(tourTimeId,1);

        if(tourTimeResponse==null)
            return "redirect:/";

        model.addAttribute("user", user);
        model.addAttribute("tourTimeResponse", tourTimeResponse);
        return "client/order-booking";
    }
    @GetMapping("booking/{id}")
    public String getPaymentBooking(Model model,
                                    @PathVariable("id") String id,
                                  @AuthenticationPrincipal CustomUserDetails user) {
        BookingResponse bookingResponse = bookingService.getBookingResponseById(id,1);

       if(bookingResponse==null)
           return "redirect:/";

        model.addAttribute("user", user);
        model.addAttribute("bookingResponse", bookingResponse);
        return "client/booking";
    }

}