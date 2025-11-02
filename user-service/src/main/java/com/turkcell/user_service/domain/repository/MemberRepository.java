package com.turkcell.user_service.domain.repository;

import com.turkcell.user_service.domain.model.Email;
import com.turkcell.user_service.domain.model.Member;
import com.turkcell.user_service.domain.model.MemberId;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);
    Optional<Member> findById(MemberId memberId);
    List<Member> findAll();
    List<Member> findAllPaged(Integer pageIndex, Integer pageSize);
    void deleteById(MemberId memberId);
    void delete(Member member);
}
