package com.turkcell.reservation_service.web.controller;

import com.turkcell.reservation_service.application.command.CancelReservationCommand;
import com.turkcell.reservation_service.application.command.CreateReservationCommand;
import com.turkcell.reservation_service.application.command.DeleteReservationCommand;
import com.turkcell.reservation_service.application.dto.CancelledReservationResponse;
import com.turkcell.reservation_service.application.dto.DeletedReservationResponse;
import com.turkcell.reservation_service.application.dto.ReservationResponse;
import com.turkcell.reservation_service.application.query.GetReservationByIdQuery;
import com.turkcell.reservation_service.application.query.ListAllMyReservationsQuery;
import com.turkcell.reservation_service.core.cqrs.CommandHandler;
import com.turkcell.reservation_service.core.cqrs.QueryHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationsController {

    private final CommandHandler<CreateReservationCommand, ReservationResponse> createReservationCommand;
    private final QueryHandler<GetReservationByIdQuery, ReservationResponse> getReservationByIdQuery;
    private final QueryHandler<ListAllMyReservationsQuery, List<ReservationResponse>> listAllMyReservationsQuery;
    private final CommandHandler<DeleteReservationCommand, DeletedReservationResponse>  deleteReservationCommand;
    private final CommandHandler<CancelReservationCommand, CancelledReservationResponse>  cancelReservationCommand;

    public ReservationsController(CommandHandler<CreateReservationCommand, ReservationResponse> createReservationCommand, QueryHandler<GetReservationByIdQuery, ReservationResponse> getReservationByIdQuery, QueryHandler<ListAllMyReservationsQuery, List<ReservationResponse>> listAllMyReservationsQuery, CommandHandler<DeleteReservationCommand, DeletedReservationResponse> deleteReservationCommand, CommandHandler<CancelReservationCommand, CancelledReservationResponse> cancelReservationCommand) {
        this.createReservationCommand = createReservationCommand;
        this.getReservationByIdQuery = getReservationByIdQuery;
        this.listAllMyReservationsQuery = listAllMyReservationsQuery;
        this.deleteReservationCommand = deleteReservationCommand;
        this.cancelReservationCommand = cancelReservationCommand;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponse createReservation(@RequestBody CreateReservationCommand command) {
        return createReservationCommand.handle(command);
    }

    @GetMapping("/{id}")
    public ReservationResponse getById(@PathVariable UUID id){
        return getReservationByIdQuery.handle(new GetReservationByIdQuery(id));
    }

    @GetMapping()
    public List<ReservationResponse> getAll(){
        return listAllMyReservationsQuery.handle(new ListAllMyReservationsQuery());
    }

    @DeleteMapping("/{id}")
    public DeletedReservationResponse delete(@PathVariable UUID id){
        return deleteReservationCommand.handle(new DeleteReservationCommand(id));
    }

    @PostMapping()
    public CancelledReservationResponse cancel(@PathVariable UUID id){
        return cancelReservationCommand.handle(new CancelReservationCommand(id));
    }
}
