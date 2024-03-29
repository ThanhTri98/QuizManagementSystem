package com.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 165139
 */
@RestController
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "<h1>WELL COME TO QUIZ MANAGEMENT SYSTEM</h1>";
    }
}
