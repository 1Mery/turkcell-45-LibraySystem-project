package com.turkcell.user_service.application.dto.response;

import java.util.UUID;

public record MemberResponse(
        UUID id,
        String firstName,
        String lastName,
        String email,
        String username,
        String phone,
        String membershipLevel,
        String memberStatus
) {
}
