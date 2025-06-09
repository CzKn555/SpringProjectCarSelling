package com.example.carselling.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WallController {
    @GetMapping("/wall")
    public String aboutUrl() {
        return "wall";
    }
}