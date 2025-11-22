package com.turkcell.fine_service.application.mapper;

import com.turkcell.fine_service.application.dto.response.DeletedFineResponse;
import com.turkcell.fine_service.application.dto.response.FineResponse;
import com.turkcell.fine_service.domain.model.Fine;
import org.springframework.stereotype.Component;

@Component
public class FineMapper {

    public FineResponse toResponse(Fine fine) {
        return new FineResponse(
                fine.id().value(),
                fine.fineAmount(),
                fine.fineDate(),
                fine.status().name(),
                fine.fineType().name(),
                fine.memberId().value(),
                fine.loanId().value()
        );
    }

    public DeletedFineResponse toDeletedResponse(Fine fine) {
        return new DeletedFineResponse(
                fine.id().value()
        );
    }
}
