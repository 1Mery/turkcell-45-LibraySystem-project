package com.turkcell.fine_service.application.command;

import com.turkcell.fine_service.application.dto.response.DeletedFineResponse;
import com.turkcell.fine_service.application.exception.FineNotFoundException;
import com.turkcell.fine_service.application.mapper.FineMapper;
import com.turkcell.fine_service.domain.model.Fine;
import com.turkcell.fine_service.domain.model.FineId;
import com.turkcell.fine_service.domain.repository.FineRepository;
import org.springframework.stereotype.Service;

//for admin
@Service
public class DeleteFineCommandHandler {

    private final FineRepository fineRepository;
    private final FineMapper fineMapper;

    public DeleteFineCommandHandler(FineRepository fineRepository, FineMapper fineMapper) {
        this.fineRepository = fineRepository;
        this.fineMapper = fineMapper;
    }

    public DeletedFineResponse delete(DeleteFineCommand command) {
        Fine fine = fineRepository.findById(new FineId(command.fineId()))
                .orElseThrow(() -> new FineNotFoundException("Fine not found"));
        fineRepository.delete(fine);

        return fineMapper.toDeletedResponse(fine);

    }
}
