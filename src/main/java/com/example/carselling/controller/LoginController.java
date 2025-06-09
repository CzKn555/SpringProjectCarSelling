package com.example.carselling.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String loginUrl(Model model){
        model.addAttribute("SubmitVar", "/submitLog");
        return "registration";
    }
}