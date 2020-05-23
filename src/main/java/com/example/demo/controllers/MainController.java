package com.example.demo.controllers;

import com.example.demo.models.Result;
import com.example.demo.services.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    private final MainService service;

    @Autowired
    public MainController(MainService service) {
        this.service = service;
    }

    @GetMapping("/combinations")
    @Validated
    public Result getCombinations(
            @RequestParam(required = true) String phoneNumber,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) throws Exception {
        return service.getPhoneCombinations(phoneNumber, page, size);
    }
}
