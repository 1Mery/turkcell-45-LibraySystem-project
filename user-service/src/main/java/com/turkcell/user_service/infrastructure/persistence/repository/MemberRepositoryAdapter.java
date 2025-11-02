package com.turkcell.user_service.infrastructure.persistence.repository;

import com.turkcell.user_service.domain.model.Member;
import com.turkcell.user_service.domain.model.MemberId;
import com.turkcell.user_service.domain.repository.MemberRepository;
import com.turkcell.user_service.infrastructure.persistence.entity.JpaMemberEntity;
import com.turkcell.user_service.infrastructure.persistence.mapper.MemberEntityMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MemberRepositoryAdapter implements MemberRepository {

    private final JpaMemberRepository memberRepository;
    private final MemberEntityMapper memberMapper;

    public MemberRepositoryAdapter(JpaMemberRepository memberRepository, MemberEntityMapper memberMapper) {
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
    }

    @Override
    public Member save(Member member) {
        JpaMemberEntity entity = memberMapper.toEntity(member);
        return memberMapper.toDomain(memberRepository.save(entity));
    }

    @Override
    public Optional<Member> findById(MemberId memberId) {
        return memberRepository
                .findById(memberId.value())
                .map(memberMapper::toDomain);
    }

    @Override
    public List<Member> findAll() {
        return memberRepository
                .findAll()
                .stream()
                .map(memberMapper::toDomain)
                .toList();
    }

    @Override
    public List<Member> findAllPaged(Integer pageIndex, Integer pageSize) {
        return memberRepository
                .findAll(PageRequest.of(pageIndex, pageSize))
                .stream()
                .map(memberMapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(MemberId memberId) {
        memberRepository.deleteById(memberId.value());

    }

    @Override
    public void delete(Member member) {
        JpaMemberEntity entity = memberMapper.toEntity(member);
        memberRepository.delete(entity);
    }

}
