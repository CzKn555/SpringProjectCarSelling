package com.example.carselling.controller;

import com.example.carselling.model.User;
import com.example.carselling.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller

public class RegistrationController {
    private UserRepository userRep;
    private PasswordEncoder passwordEncoder;
    public RegistrationController(UserRepository userRep,
                                  PasswordEncoder passwordEncoder) {
        this.userRep = userRep;
        this.passwordEncoder = passwordEncoder;}

    @GetMapping("/registration")
    public String registerUrl(Model model){
        model.addAttribute("regAttr", "register");
        model.addAttribute("SubmitVar", "/submitReg");
        return "registration";
    }
    @PostMapping("/submitReg")
    public String registerRedirect(
            @ModelAttribute User user,
            Model model,
            RedirectAttributes redirectAttributes){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRep.save(user);
        redirectAttributes.addFlashAttribute("successMessage", "");
        return "redirect:/login";
    }
}
