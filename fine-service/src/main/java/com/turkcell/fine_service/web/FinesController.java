package com.turkcell.fine_service.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/fines")
public class FinesController {

    @GetMapping
    public String get(){
        return "This is the Fine Service API";
    }
}
