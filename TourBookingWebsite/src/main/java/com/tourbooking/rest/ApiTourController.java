package com.tourbooking.rest;

import com.tourbooking.dto.response.FindTourResponse;
import com.tourbooking.dto.response.TourResponse;
import com.tourbooking.model.Tour;
import com.tourbooking.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/tours")
public class ApiTourController {
    @Autowired
    private TourService tourService;
    @GetMapping
    public Page<FindTourResponse> getTours(@RequestParam(required = false) Integer min,
                                           @RequestParam(required = false) Integer max,
                                           @RequestParam(required = false) String sort,
                                           @RequestParam(required = false) String search,
                                           @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd")Date departureDate,
                                           @RequestParam(required = false) Integer categoryId,
                                           Pageable pageable) {
        return tourService.findTours(min,max,search,categoryId,departureDate,pageable, sort);
    }
}
