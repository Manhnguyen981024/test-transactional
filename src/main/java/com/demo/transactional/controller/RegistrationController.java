package com.demo.transactional.controller;

import com.demo.transactional.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class RegistrationController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public String registerForm() {
        return "registration";
    }

    //RegistrationForm
    @PostMapping
    public String processRegistration() {
//        userRepository.save(new User("admin", "admin", passwordEncoder.encode("admin")));
        return "redirect:/login";
    }
}
