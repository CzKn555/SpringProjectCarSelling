package com.example.carselling.controller;

import com.example.carselling.model.BookedCar;
import com.example.carselling.model.Car;
import com.example.carselling.repository.BookedCarRepository;
import com.example.carselling.repository.CarRepository;

import com.example.carselling.service.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

@Controller
public class SearchController {
    private CarRepository carRepo;
    private BookedCarRepository bookedCarRepository;
    public SearchController(CarRepository carRepo, BookedCarRepository bookedCarRepository)
    {
        this.bookedCarRepository = bookedCarRepository;
        this.carRepo = carRepo;
    }

    @GetMapping("/search")
    public String searchRedirect(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(required = false) String make,
            @RequestParam(required = false) String brandModel,
            @RequestParam(required = false) Short minYear,
            @RequestParam(required = false) Short maxYear,
            @RequestParam(required = false) Integer minCoast,
            @RequestParam(required = false) Integer maxCoast,
            @RequestParam(required = false) String color,
            Model model) {
        List<Long> carIds = (userDetails != null)
                ? bookedCarRepository.findCarIdsByUserId(userDetails.getUserId())
                : Collections.emptyList();
        List<Car> cars = carRepo.searchCars(minYear, maxYear, maxCoast, minCoast, make, brandModel, color);
        int countBookedCars = carIds.size();
        model.addAttribute("countBookedCars", countBookedCars);
        model.addAttribute("carIds", carIds);
        model.addAttribute("cars", cars);
        model.addAttribute("search", "yes");
        return "index";
    }
}
