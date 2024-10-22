package com.tourbooking.controller;

import com.tourbooking.model.Tour;
import com.tourbooking.model.TourTime;
import com.tourbooking.service.TourService;
import com.tourbooking.service.TourTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// thay the autowired va tu tao private final,
@Controller
public class HomeController {

    @Autowired
    private  TourService tourService;
    
    @Autowired
    private  TourTimeService tourTimeService;

    @GetMapping("/")
    public String index() {
        return "index"; // Trả về tên template mà không cần phần mở rộng
    }

    @GetMapping("/sample")
    public String getDemoSampleClient(Model model) {
        return "client/sample";
    }

    @GetMapping("/tour-detail/{id}")
    public String getTourDetail(@PathVariable("id") String id, Model model) {
        Tour tour = tourService.getTourById(id);
        if (tour == null) {
           return "redirect:/";
        }

        List<String> listImage = List.of("/img/54.jpg", "/img/54.jpg", "/img/55.jpg","/img/55.jpg","/img/55.jpg","/img/55.jpg");

        List<Map<String, Object>> groupedTourTimes = tourTimeService.groupTourTimesByMonth(id);


        model.addAttribute("locations", groupedTourTimes);
        model.addAttribute("tour", tour);
        model.addAttribute("listImage", listImage);

        return "client/tour-detail";

    }

    @GetMapping("/order-booking")
    public String getOrderBooking(Model model) {
        return "client/order-booking";
    }
}
