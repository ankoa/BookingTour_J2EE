package com.tourbooking.controller.client;

import com.tourbooking.dto.response.TourImageResponse;
import com.tourbooking.dto.response.TourResponse;
import com.tourbooking.security.CustomUserDetails;
import com.tourbooking.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TourController {

    @Autowired
    private TourService tourService;

    @GetMapping("/tour/{id}")
    public String getTour(@PathVariable("id") String id,
                          Model model,
                          @AuthenticationPrincipal CustomUserDetails user) {
        TourResponse tourResponse = tourService.getTourResponse(id, 1);

        if (tourResponse == null)
            return "redirect:/";

        //add image default
        if (tourResponse.getTourImageResponses().isEmpty()) {
            List<TourImageResponse> listTourImage = new ArrayList<>();
            listTourImage.add(new TourImageResponse(999, "/client/img/54.jpg", 1));
            listTourImage.add(new TourImageResponse(998, "/client/img/55.jpg", 0));
            tourResponse.setTourImageResponses(listTourImage);
        }
        if (tourResponse.getTourImageResponse() == null)
            tourResponse.setTourImageResponse(new TourImageResponse(997, "/client/img/54.jpg", 1));

        model.addAttribute("user", user);
        model.addAttribute("tourResponse", tourResponse);
        return "client/tour-detail";
    }
}
