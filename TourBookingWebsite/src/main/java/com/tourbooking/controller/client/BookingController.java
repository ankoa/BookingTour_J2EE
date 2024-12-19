package com.tourbooking.controller.client;

import com.tourbooking.dto.response.BookingResponse;
import com.tourbooking.dto.response.TourTimeResponse;
import com.tourbooking.security.CustomUserDetails;
import com.tourbooking.service.BookingService;
import com.tourbooking.service.TourTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookingController {
    @Autowired
    BookingService bookingService;

    @Autowired
    TourTimeService tourTimeService;

    @GetMapping("/order-booking")
    public String getOrderBooking(Model model,
                                  @RequestParam(required = true) String tourTimeId,
                                  @AuthenticationPrincipal CustomUserDetails user) {
        TourTimeResponse tourTimeResponse = tourTimeService.getTourTimeResponseById(tourTimeId, 1);

        if (tourTimeResponse == null)
            return "redirect:/";

        model.addAttribute("user", user);
        model.addAttribute("tourTimeResponse", tourTimeResponse);
        return "client/order-booking";
    }

    @GetMapping("booking/{id}")
    public String getPaymentBooking(Model model,
                                    @PathVariable("id") String id,
                                    @AuthenticationPrincipal CustomUserDetails user) {
        BookingResponse bookingResponse = bookingService.getBookingResponseById(id, 1);

        if (bookingResponse == null)
            return "redirect:/";

        model.addAttribute("user", user);
        model.addAttribute("bookingResponse", bookingResponse);
        return "client/booking";
    }
}
