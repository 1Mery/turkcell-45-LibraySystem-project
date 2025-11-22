package com.turkcell.fine_service.application.query;

import com.turkcell.fine_service.application.dto.response.FineResponse;
import com.turkcell.fine_service.application.mapper.FineMapper;
import com.turkcell.fine_service.domain.model.LoanId;
import com.turkcell.fine_service.domain.repository.FineRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class GetFinesByLoanQueryHandler {

    private final FineRepository fineRepository;
    private final FineMapper fineMapper;

    public GetFinesByLoanQueryHandler(FineRepository fineRepository, FineMapper fineMapper) {
        this.fineRepository = fineRepository;
        this.fineMapper = fineMapper;
    }

    public List<FineResponse> getFinesByLoanId(@Valid GetFinesByLoanQuery query) {
        return fineRepository.findByLoanId(new LoanId(query.loanId()))
                .stream()
                .map(fineMapper::toResponse)
                .toList();
    }
}
