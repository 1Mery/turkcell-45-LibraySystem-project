package com.turkcell.reservation_service.application.query;

import com.turkcell.reservation_service.application.dto.ReservationResponse;
import com.turkcell.reservation_service.core.cqrs.Query;

import java.util.List;
import java.util.UUID;

public record GetReservationsByMemberQuery(UUID memberId) implements Query<List<ReservationResponse>> {
}


