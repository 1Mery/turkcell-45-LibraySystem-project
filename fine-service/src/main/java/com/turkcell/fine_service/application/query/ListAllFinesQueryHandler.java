package com.turkcell.fine_service.application.query;

import com.turkcell.fine_service.application.dto.response.FineResponse;
import com.turkcell.fine_service.application.mapper.FineMapper;
import com.turkcell.fine_service.domain.repository.FineRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class ListAllFinesQueryHandler {

    private final FineRepository fineRepository;
    private final FineMapper fineMapper;

    public ListAllFinesQueryHandler(FineRepository fineRepository, FineMapper fineMapper) {
        this.fineRepository = fineRepository;
        this.fineMapper = fineMapper;
    }

    public List<FineResponse> getAllFines(@Valid ListAllFinesQuery query) {
        return fineRepository
                .findAllPaged(query.pageIndex(), query.pageSize())
                .stream()
                .map(fineMapper::toResponse)
                .toList();
    }
}
