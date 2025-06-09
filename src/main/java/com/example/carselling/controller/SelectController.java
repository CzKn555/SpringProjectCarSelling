package com.example.carselling.controller;

import com.example.carselling.model.BookedCar;
import com.example.carselling.model.Car;
import com.example.carselling.model.User;
import com.example.carselling.repository.BookedCarRepository;
import com.example.carselling.repository.CarRepository;
import com.example.carselling.repository.UserRepository;
import com.example.carselling.service.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class SelectController {
    private UserRepository userRepository;
    private CarRepository carRepository;
    private BookedCarRepository bookedCarRepository;

    public SelectController(UserRepository userRepository,
                            CarRepository carRepository,
                            BookedCarRepository bookedCarRepository)
    {
        this.userRepository = userRepository;
        this.carRepository = carRepository;
        this.bookedCarRepository = bookedCarRepository;
    }
    @GetMapping("/search/select")
    public String selectUrl(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam Long carId
    )
    {
        Long userId = userDetails.getUserId();
        User user = userRepository.findById(userId).orElseThrow();
        Car car = carRepository.findById(carId).orElseThrow();
        BookedCar bookedCar = new BookedCar();
        bookedCar.setCreated_time(LocalDateTime.now());
        bookedCar.setCar(car);
        bookedCar.setUser(user);

        bookedCarRepository.save(bookedCar);
        return "redirect:/records";
    }
}
