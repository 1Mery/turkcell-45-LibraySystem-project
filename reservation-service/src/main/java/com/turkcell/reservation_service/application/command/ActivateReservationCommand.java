package com.turkcell.reservation_service.application.command;

import com.turkcell.reservation_service.application.dto.ReservationResponse;
import com.turkcell.reservation_service.core.cqrs.Command;

import java.util.UUID;

public record ActivateReservationCommand(UUID reservationId) implements Command<ReservationResponse> {
}
