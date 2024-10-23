package com.tourbooking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index"; // Trả về tên template mà không cần phần mở rộng
    }

    @GetMapping("/sample")
    public String getDemoSampleClient(Model model) {
        return "client/sample";
    }

    @GetMapping("/find-tour")
    public String getFindTour(Model model) {
        return "client/find-tour";
    }

    @GetMapping("/tour-detail")
    public String getTourDetail(Model model) {
        return "client/tour-detail";
    }

    @GetMapping("/order-booking")
    public String getOrderBooking(Model model) {
        return "client/order-booking";
    }
}
