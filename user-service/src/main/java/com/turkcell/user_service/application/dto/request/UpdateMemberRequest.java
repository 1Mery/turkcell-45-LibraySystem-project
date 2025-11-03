package com.turkcell.user_service.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateMemberRequest(
        @NotBlank @Size(min = 3, max = 50) String firstName,
        @NotBlank @Size(min = 3, max = 50) String lastName,
        @NotBlank @Email @Pattern(regexp = "^[A-Za-z0-9._%+-]+@(?:[A-Za-z0-9-]+\\.)*edu\\.tr$") String email,
        @NotBlank String password,
        @NotBlank @Size(min = 3, max = 20) String username,
        @NotBlank @Pattern(regexp = "^5[0-9]{9}$") String phone
) {
}
