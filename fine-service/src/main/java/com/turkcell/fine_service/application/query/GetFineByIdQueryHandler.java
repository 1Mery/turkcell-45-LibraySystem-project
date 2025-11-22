package com.turkcell.fine_service.application.query;

import com.turkcell.fine_service.application.dto.response.FineResponse;
import com.turkcell.fine_service.application.exception.FineNotFoundException;
import com.turkcell.fine_service.application.mapper.FineMapper;
import com.turkcell.fine_service.domain.model.Fine;
import com.turkcell.fine_service.domain.model.FineId;
import com.turkcell.fine_service.domain.repository.FineRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class GetFineByIdQueryHandler {

    private final FineRepository fineRepository;
    private final FineMapper fineMapper;

    public GetFineByIdQueryHandler(FineRepository fineRepository, FineMapper fineMapper) {
        this.fineRepository = fineRepository;
        this.fineMapper = fineMapper;
    }

    public FineResponse getFineByIdQuery(@Valid GetFineByIdQuery query) {
        Fine fine = fineRepository.findById(new FineId(query.fineId()))
                .orElseThrow(() -> new FineNotFoundException("Fine not found"));
        return fineMapper.toResponse(fine);
    }
}
