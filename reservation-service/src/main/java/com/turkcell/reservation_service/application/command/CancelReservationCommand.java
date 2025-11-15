package com.turkcell.reservation_service.application.command;

import com.turkcell.reservation_service.application.dto.CancelledReservationResponse;
import com.turkcell.reservation_service.core.cqrs.Command;

import java.util.UUID;

public record CancelReservationCommand(UUID reservationId) implements Command<CancelledReservationResponse> {
}
