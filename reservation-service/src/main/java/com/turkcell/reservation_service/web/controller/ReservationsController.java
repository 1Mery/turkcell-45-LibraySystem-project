package com.turkcell.reservation_service.web.controller;

import com.turkcell.reservation_service.application.command.*;
import com.turkcell.reservation_service.application.dto.*;
import com.turkcell.reservation_service.application.query.*;
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
    private final QueryHandler<ListAllReservationsQuery, List<ReservationResponse>> listAllMyReservationsQuery;
    private final CommandHandler<DeleteReservationCommand, DeletedReservationResponse> deleteReservationCommand;
    private final CommandHandler<CancelReservationCommand, CancelledReservationResponse> cancelReservationCommand;
    private final CommandHandler<ActivateReservationCommand, ReservationResponse> activateReservationCommand;
    private final QueryHandler<GetReservationsByMemberQuery, List<ReservationResponse>> getReservationsByMemberQuery;
    private final QueryHandler<GetReservationsByBookQuery, List<ReservationResponse>> getReservationsByBookQuery;

    public ReservationsController(
            CommandHandler<CreateReservationCommand, ReservationResponse> createReservationCommand,
            QueryHandler<GetReservationByIdQuery, ReservationResponse> getReservationByIdQuery,
            QueryHandler<ListAllReservationsQuery, List<ReservationResponse>> listAllMyReservationsQuery,
            CommandHandler<DeleteReservationCommand, DeletedReservationResponse> deleteReservationCommand,
            CommandHandler<CancelReservationCommand, CancelledReservationResponse> cancelReservationCommand,
            CommandHandler<ActivateReservationCommand, ReservationResponse> activateReservationCommand,
            QueryHandler<GetReservationsByMemberQuery, List<ReservationResponse>> getReservationsByMemberQuery,
            QueryHandler<GetReservationsByBookQuery, List<ReservationResponse>> getReservationsByBookQuery) {
        this.createReservationCommand = createReservationCommand;
        this.getReservationByIdQuery = getReservationByIdQuery;
        this.listAllMyReservationsQuery = listAllMyReservationsQuery;
        this.deleteReservationCommand = deleteReservationCommand;
        this.cancelReservationCommand = cancelReservationCommand;
        this.activateReservationCommand = activateReservationCommand;
        this.getReservationsByMemberQuery = getReservationsByMemberQuery;
        this.getReservationsByBookQuery = getReservationsByBookQuery;
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
        return listAllMyReservationsQuery.handle(new ListAllReservationsQuery());
    }

    @DeleteMapping("/{id}")
    public DeletedReservationResponse delete(@PathVariable UUID id){
        return deleteReservationCommand.handle(new DeleteReservationCommand(id));
    }

    @PatchMapping("/{id}")
    public CancelledReservationResponse cancel(
            @PathVariable UUID id,
            @RequestParam(required = false) String reason){
        return cancelReservationCommand.handle(new CancelReservationCommand(id, reason));
    }
    
    /**
     * Belirli bir rezervasyonu aktif eder (PENDING -> ACTIVE)
     */
    @PostMapping("/{id}/activate")
    public ReservationResponse activateReservation(@PathVariable UUID id) {
        return activateReservationCommand.handle(new ActivateReservationCommand(id));
    }

    /**
     * Belirli bir üyenin tüm rezervasyonlarını döner
     */
    @GetMapping("/members/{memberId}")
    public List<ReservationResponse> getReservationsByMember(@PathVariable UUID memberId) {
        return getReservationsByMemberQuery.handle(new GetReservationsByMemberQuery(memberId));
    }

    /**
     * Belirli bir kitap için rezervasyonları döner (opsiyonel olarak status'e göre filtreler)
     */
    @GetMapping("/books/{bookId}")
    public List<ReservationResponse> getReservationsByBook(
            @PathVariable UUID bookId,
            @RequestParam(required = false) String status) {
        return getReservationsByBookQuery.handle(new GetReservationsByBookQuery(bookId, status));
    }
    
//    /**
//     * Kitap müsait olduğunda otomatik olarak en yüksek priority'ye sahip rezervasyonu aktif eder
//     */
//    @PostMapping("/books/{bookId}/activate-when-available")
//    public ReservationResponse activateReservationWhenAvailable(@PathVariable UUID bookId) {
//        return activateReservationWhenAvailableCommand.handle(new ActivateReservationWhenAvailableCommand(bookId));
//    }
//
//    /**
//     * Belirli bir kitap için kuyruk pozisyonlarını günceller
//     */
//    @PostMapping("/books/{bookId}/update-queue")
//    public List<ReservationResponse> updateQueuePositions(@PathVariable UUID bookId) {
//        return updateQueuePositionsCommand.handle(new UpdateQueuePositionsCommand(bookId));
//    }
    

}
