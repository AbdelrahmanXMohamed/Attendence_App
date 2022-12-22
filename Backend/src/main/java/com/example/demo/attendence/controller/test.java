package com.example.demo.attendence.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class test {
    @GetMapping
    public String test()
    {
        return "Hello world";
    }
}
