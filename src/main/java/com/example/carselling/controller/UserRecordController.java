package com.example.carselling.controller;

import com.example.carselling.model.BookedCar;
import com.example.carselling.repository.BookedCarRepository;
import com.example.carselling.service.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserRecordController {
    private BookedCarRepository bookedCarRepository;
    public UserRecordController(BookedCarRepository bookedCarRepository) {
        this.bookedCarRepository = bookedCarRepository;
    }
    @GetMapping("/records")
    public String userRecordUrl(@AuthenticationPrincipal CustomUserDetails userDetails, Model model)
    {
        Long userId = userDetails.getUserId();
        List<BookedCar> userBookings = bookedCarRepository.findByUserId(userId);
        model.addAttribute("bookings", userBookings);
        return "userRecord";
    }
}