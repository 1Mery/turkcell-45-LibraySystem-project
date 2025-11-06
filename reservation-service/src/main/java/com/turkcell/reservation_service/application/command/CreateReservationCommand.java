package com.turkcell.reservation_service.application.command;

import com.turkcell.reservation_service.application.dto.ReservationResponse;
import com.turkcell.reservation_service.core.cqrs.Command;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateReservationCommand(
        @NotNull UUID memberId,
        @NotNull UUID bookId) implements Command<ReservationResponse> {
}
