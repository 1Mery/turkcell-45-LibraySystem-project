package com.turkcell.reservation_service.application.query;

import com.turkcell.reservation_service.application.dto.ReservationResponse;
import com.turkcell.reservation_service.core.cqrs.Query;

import java.util.List;

public record ListAllReservationsQuery() implements Query<List<ReservationResponse>> {
}
