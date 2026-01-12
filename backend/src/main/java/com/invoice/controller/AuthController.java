package com.invoice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @GetMapping("/")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password
    ) {
        if ("admin".equals(username) && "admin".equals(password)) {
            return "redirect:/invoice";
        }
        return "redirect:/?error";
    }
}
