package com.turkcell.loanservice.infrastructure.persistence.repository;

import com.turkcell.loanservice.domain.model.Loan;
import com.turkcell.loanservice.domain.model.LoanId;
import com.turkcell.loanservice.domain.model.LoanStatus;
import com.turkcell.loanservice.domain.model.UserId;
import com.turkcell.loanservice.domain.repository.LoanRepository;
import com.turkcell.loanservice.infrastructure.persistence.entity.LoanEntity;
import com.turkcell.loanservice.infrastructure.persistence.mapper.LoanEntityMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class LoanAdapterRepository implements LoanRepository {

    private final SpringDataLoanRepository repository;
    private final LoanEntityMapper mapper;

    public LoanAdapterRepository(SpringDataLoanRepository repository, LoanEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Loan save(Loan loan) {
        LoanEntity entity=mapper.toEntity(loan);
        repository.save(entity);
        return mapper.toDomain(entity);
    }

    @Override
    public Optional<Loan> findById(LoanId loanId) {
        return repository.findById(loanId.value()).
                map(mapper::toDomain);
    }

    @Override
    public List<Loan> findAllPaged(Integer pageIndex, Integer pageSize) {
        PageRequest pageable = PageRequest.of(pageIndex, pageSize);
        return repository.findAll(pageable)
                .getContent()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(LoanId loanId) {
        repository.deleteById(loanId.value());
    }

    @Override
    public List<Loan> findByUserIdAndStatus(UserId userId, LoanStatus status) {
        return repository.findByUserIdAndStatus(userId.value(), status)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Loan> findAllByStatus(LoanStatus status) {
        List<LoanEntity> entities = repository.findAllByStatus(status);

        return entities.stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
