package com.turkcell.user_service.application.dto.response;

import java.util.UUID;

/**
 * silme işleminden sonra boş dönmesin.
 */
public record DeletedMemberResponse(UUID id) {
}
