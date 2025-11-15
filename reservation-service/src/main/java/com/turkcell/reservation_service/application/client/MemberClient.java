package com.turkcell.reservation_service.application.client;

import com.turkcell.reservation_service.application.dto.MemberResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "user-service", path = "/api/v1/users")
public interface MemberClient {

    @GetMapping("/{id}")
    MemberResponse getById(@PathVariable UUID id);

}
