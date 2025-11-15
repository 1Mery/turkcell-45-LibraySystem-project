package com.turkcell.reservation_service.application.query;

import com.turkcell.reservation_service.application.dto.ReservationResponse;
import com.turkcell.reservation_service.core.cqrs.Query;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record GetReservationByIdQuery(@Positive @NotNull UUID id) implements Query<ReservationResponse> {
}
