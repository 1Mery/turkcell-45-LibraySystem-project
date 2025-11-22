package com.turkcell.fine_service.infrastructure.persistence.mapper;

import com.turkcell.fine_service.domain.model.Fine;
import com.turkcell.fine_service.domain.model.FineId;
import com.turkcell.fine_service.domain.model.LoanId;
import com.turkcell.fine_service.domain.model.MemberId;
import com.turkcell.fine_service.infrastructure.persistence.entity.JpaFineEntity;
import org.springframework.stereotype.Component;

@Component
public class JpaFineMapper {

    public JpaFineEntity toEntity(Fine fine) {
        return new JpaFineEntity(
                fine.id().value(),
                fine.fineAmount(),
                fine.fineDate(),
                fine.status(),
                fine.fineType(),
                fine.memberId().value(),
                fine.loanId().value()
        );
    }

    public Fine toDomain(JpaFineEntity entity) {
        return Fine.rehydrate(
                new FineId(entity.id()),
                entity.fineAmount(),
                entity.fineDate(),
                entity.fineStatus(),
                entity.fineType(),
                new MemberId(entity.memberId()),
                new LoanId(entity.loanId())
        );

    }
}
