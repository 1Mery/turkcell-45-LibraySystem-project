package com.turkcell.fine_service.infrastructure.persistence.repository;

import com.turkcell.fine_service.domain.model.Fine;
import com.turkcell.fine_service.domain.model.FineId;
import com.turkcell.fine_service.domain.model.LoanId;
import com.turkcell.fine_service.domain.model.MemberId;
import com.turkcell.fine_service.domain.repository.FineRepository;
import com.turkcell.fine_service.infrastructure.persistence.entity.JpaFineEntity;
import com.turkcell.fine_service.infrastructure.persistence.mapper.JpaFineMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class FineRepositoryAdapter implements FineRepository {

    private final JpaFineRepository fineRepository;
    private final JpaFineMapper fineMapper;

    public FineRepositoryAdapter(JpaFineRepository fineRepository, JpaFineMapper fineMapper) {
        this.fineRepository = fineRepository;
        this.fineMapper = fineMapper;
    }

    @Override
    public Fine save(Fine fine) {
        JpaFineEntity entity = fineMapper.toEntity(fine);
        entity = fineRepository.save(entity);
        return fineMapper.toDomain(entity);
    }

    @Override
    public List<Fine> findAllPaged(Integer pageIndex, Integer pageSize) {
        return fineRepository
                .findAll(PageRequest.of(pageSize, pageSize))
                .stream()
                .map(fineMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Fine> findById(FineId id) {
        return fineRepository
                .findById(id.value())
                .map(fineMapper::toDomain);
    }

    @Override
    public void deleteById(FineId id) {
        fineRepository.deleteById(id.value());
    }

    @Override
    public void delete(Fine fine) {
        JpaFineEntity entity = fineMapper.toEntity(fine);
        fineRepository.delete(entity);
    }

    @Override
    public List<Fine> findByMemberId(MemberId memberId) {
        return fineRepository
                .findByMemberId(memberId.value())
                .stream()
                .map(fineMapper::toDomain)
                .toList();
    }

    @Override
    public List<Fine> findByLoanId(LoanId loanId) {
        return fineRepository
                .findByLoanId(loanId.value())
                .stream()
                .map(fineMapper::toDomain)
                .toList();
    }
}
