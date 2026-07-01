package com.glottia.backend.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Value("${app.frontend-url:https://glottia-frontend.onrender.com}")
    private String frontendUrl;

    @GetMapping("/")
    public String home() {
        return "redirect:" + frontendUrl;
    }
}
