package com.turkcell.fine_service.web.controller;

import com.turkcell.fine_service.application.command.DeleteFineCommand;
import com.turkcell.fine_service.application.command.DeleteFineCommandHandler;
import com.turkcell.fine_service.application.dto.response.DeletedFineResponse;
import com.turkcell.fine_service.application.dto.response.FineResponse;
import com.turkcell.fine_service.application.query.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/fines")
public class FinesController {

    private final ListAllFinesQueryHandler listAllQueryHandler;
    private final GetFineByIdQueryHandler getByIdQueryHandler;
    private final GetFinesByLoanQueryHandler getByLoanQueryHandler;
    private final GetFinesByMemberQueryHandler getByMemberQueryHandler;
    private final DeleteFineCommandHandler deleteCommandHandler;

    public FinesController(ListAllFinesQueryHandler listAllQueryHandler, GetFineByIdQueryHandler getByIdQueryHandler, GetFinesByLoanQueryHandler getByLoanQueryHandler, GetFinesByMemberQueryHandler getByMemberQueryHandler, DeleteFineCommandHandler deleteCommandHandler) {
        this.listAllQueryHandler = listAllQueryHandler;
        this.getByIdQueryHandler = getByIdQueryHandler;
        this.getByLoanQueryHandler = getByLoanQueryHandler;
        this.getByMemberQueryHandler = getByMemberQueryHandler;
        this.deleteCommandHandler = deleteCommandHandler;
    }


    @GetMapping
    public List<FineResponse> getAllFines(@RequestParam Integer pageSize, @RequestParam Integer pageIndex) {
        return listAllQueryHandler.getAllFines(new ListAllFinesQuery(pageSize, pageIndex));
    }

    @GetMapping("/{fineId}")
    public FineResponse getFinesById(@PathVariable UUID fineId) {
        return getByIdQueryHandler.getFineByIdQuery(new GetFineByIdQuery(fineId));
    }

    @GetMapping("/loan/{loanId}")
    public List<FineResponse> getFinesByLoanId(@PathVariable UUID loanId) {
        return getByLoanQueryHandler.getFinesByLoanId(new GetFinesByLoanQuery(loanId));
    }

    @GetMapping("/member/{memberId}")
    public List<FineResponse> getFinesByMemberId(@PathVariable UUID memberId) {
        return getByMemberQueryHandler.getFinesByMemberId(new GetFinesByMemberQuery(memberId));
    }

    @DeleteMapping("/{fineId}")
    public DeletedFineResponse deleteFinesById(@PathVariable UUID fineId) {
        return deleteCommandHandler.delete(new DeleteFineCommand(fineId));
    }

}
