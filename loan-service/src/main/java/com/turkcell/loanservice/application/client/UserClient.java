package com.turkcell.loanservice.application.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/api/v1/users/{id}/email")
    String getEmail(@PathVariable UUID id);

    @GetMapping("/api/v1/users/{id}/name")
    String getName(@PathVariable UUID id);
}
