package com.example.carselling.controller;

import com.example.carselling.model.BookedCar;
import com.example.carselling.repository.BookedCarRepository;
import com.example.carselling.repository.CarRepository;
import com.example.carselling.repository.UserRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ManagerController {

    private BookedCarRepository bookedCarRepository;

    private UserRepository userRepository;

    private CarRepository carRepository;

    public ManagerController(
            BookedCarRepository bookedCarRepository,
            UserRepository userRepository,
            CarRepository carRepository) {
        this.bookedCarRepository = bookedCarRepository;
        this.userRepository = userRepository;
        this.carRepository = carRepository;
    }

    @GetMapping("/dashboard")
    public String viewBookings(Model model) {
        List<BookedCar> bookings = bookedCarRepository.findAllWithUsersAndCars();
        model.addAttribute("bookings", bookings);
        return "managerDashboard/managerDashboard";
    }

    @PostMapping("/dashboard/update-time/{id}")
    public String updateBookingTime(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime planned_time,
            RedirectAttributes redirectAttributes) {
        try {
            BookedCar booking = bookedCarRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
            booking.setPlaned_time(planned_time);
            bookedCarRepository.save(booking);
            redirectAttributes.addFlashAttribute("success", "Booking time updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error updating booking time: " + e.getMessage());
        }
        return "redirect:/dashboard";
    }

    @PostMapping("/dashboard/delete/{id}")
    public String deleteBooking(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            bookedCarRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Booking deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting booking: " + e.getMessage());
        }
        return "redirect:/dashboard";
    }
}