package com.example.carselling.controller;

import com.example.carselling.model.BookedCar;
import com.example.carselling.model.Car;
import com.example.carselling.model.Employee;
import com.example.carselling.model.User;
import com.example.carselling.repository.BookedCarRepository;
import com.example.carselling.repository.CarRepository;
import com.example.carselling.repository.EmployeeRepository;
import com.example.carselling.repository.UserRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class AdminController {
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final EmployeeRepository employeeRepository;
    private final BookedCarRepository bookedCarRepository;

    private PasswordEncoder passwordEncoder;

    public AdminController(UserRepository userRepository, CarRepository carRepository,
                           EmployeeRepository employeeRepository, BookedCarRepository bookedCarRepository,
                           PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.carRepository = carRepository;
        this.employeeRepository = employeeRepository;
        this.bookedCarRepository = bookedCarRepository;
    }

    @GetMapping("/admin")
    public String adminUrl() {
        return "adminDashboard/adminDashboard";
    }

    @GetMapping("/admin/users")
    public String userManagement(Model model) {
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("newUser", new User());
        return "adminDashboard/usersControl";
    }

    @PostMapping("/admin/users/add")
    public String addUser(@ModelAttribute User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/admin/users";
    }

    //Здесь обработка CARS
    @GetMapping("/admin/cars")
    public String carsUrl(Model model) {
        model.addAttribute("cars", carRepository.findAll());
        model.addAttribute("newCar", new Car());
        return "adminDashboard/carsControl";
    }
    @PostMapping("/admin/cars/add")
    public String addCar(@ModelAttribute Car car) {
        carRepository.save(car);
        return "redirect:/admin/cars";
    }

    @GetMapping("/admin/cars/delete/{id}")
    public String deleteCar(@PathVariable Long id) {
        carRepository.deleteById(id);
        return "redirect:/admin/cars";
    }

    //Здесь обработка EMPLOYEES
    @GetMapping("/admin/employees")
    public String employeeManagement(Model model) {
        model.addAttribute("employees", employeeRepository.findAllWithUsers());
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("newEmployee", new Employee());
        model.addAttribute("roles", List.of("ADMIN", "MANAGER", "SUPERVISOR"));
        model.addAttribute("positions", List.of("Director", "Manager", "Mechanic"));
        return "adminDashboard/employeeControl";
    }

    @PostMapping("/admin/employees/add")
    public String addEmployee(@ModelAttribute Employee employee,
                              @RequestParam Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + userId));
        employee.setUser(user);
        employeeRepository.save(employee);
        return "redirect:/admin/employees";
    }

    @GetMapping("/admin/employees/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeRepository.deleteById(id);
        return "redirect:/admin/employees";
    }

    //Здесь обработка BOOKEDCARS
    @GetMapping("/admin/bookings")
    public String bookingManagement(Model model) {
        model.addAttribute("bookings", bookedCarRepository.findAllWithUsersAndCars());
        model.addAttribute("users", userRepository.findAll()); // Для выпадающего списка
        model.addAttribute("cars", carRepository.findAll()); // Для выпадающего списка
        model.addAttribute("newBooking", new BookedCar());
        return "adminDashboard/booksControl";
    }

    @PostMapping("/admin/bookings/add")
    public String addBooking(@ModelAttribute BookedCar bookedCar,
                             @RequestParam Long userId,
                             @RequestParam Long carId,
                             @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime planned_time) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + userId));
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid car Id:" + carId));

        bookedCar.setUser(user);
        bookedCar.setCar(car);
        bookedCar.setPlaned_time(planned_time);
        bookedCar.setCreated_time(LocalDateTime.now());

        bookedCarRepository.save(bookedCar);
        return "redirect:/admin/bookings";
    }

    @GetMapping("/admin/bookings/delete/{id}")
    public String deleteBooking(@PathVariable Long id) {
        bookedCarRepository.deleteById(id);
        return "redirect:/admin/bookings";
    }
}
