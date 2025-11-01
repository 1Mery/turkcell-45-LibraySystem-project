package com.turkcell.reservation_service.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationsController {

    @GetMapping
    public String get(){
        return "This is the Reservation Service API";
    }
}
