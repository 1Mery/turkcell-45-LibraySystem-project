package com.turkcell.reservation_service.web;

import com.turkcell.reservation_service.application.command.CreateReservationCommand;
import com.turkcell.reservation_service.application.dto.ReservationResponse;
import com.turkcell.reservation_service.application.service.CreateReservationUseCase;
import com.turkcell.reservation_service.core.cqrs.CommandHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationsController {

    private final CommandHandler<CreateReservationCommand, ReservationResponse> createReservationCommand;

    public ReservationsController(CommandHandler<CreateReservationCommand, ReservationResponse> createReservationCommand) {
        this.createReservationCommand = createReservationCommand;
    }

    @PostMapping
    public ReservationResponse createReservation(CreateReservationCommand command){
        return createReservationCommand.handle(command);
    }
}
