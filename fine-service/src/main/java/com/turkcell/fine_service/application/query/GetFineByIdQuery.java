package com.turkcell.fine_service.application.query;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record GetFineByIdQuery(@NotNull UUID fineId) {
}
