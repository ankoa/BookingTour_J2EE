package com.tourbooking.controller;

import com.tourbooking.dto.response.TourTimeResponse;
import com.tourbooking.model.Tour;
import com.tourbooking.service.TourService;
import com.tourbooking.service.TourTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class HomeController {

    @Autowired
    private TourService tourService;

    @Autowired
    private TourTimeService tourTimeService;

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

    @GetMapping("/tour-detail/{id}")
    public String getTourDetail(@PathVariable("id") String id, Model model) {
        Tour tour = tourService.getTourById(id);
        if (tour == null) {
            return "redirect:/";
        }

        List<String> listImage = tourService.getListImageUrl(id);
        List<TourTimeResponse> tourTimes = tourTimeService.getListTourTimeResponseByTourId(id);

        model.addAttribute("tourTimes", tourTimes);
        model.addAttribute("tour", tour);
        model.addAttribute("listImage", listImage);

        return "client/tour-detail";

    }

    @GetMapping("/order-booking")
    public String getOrderBooking(Model model, @RequestParam(required = true) String tourTimeId) {
        TourTimeResponse tourTimeResponse = tourTimeService.getTourTimeResponseById(tourTimeId);
        String tourName = tourTimeService.getTourName(tourTimeId);
        model.addAttribute("tourTimeResponse", tourTimeResponse);
        model.addAttribute("tourName", tourName);
        return "client/order-booking";
    }


}