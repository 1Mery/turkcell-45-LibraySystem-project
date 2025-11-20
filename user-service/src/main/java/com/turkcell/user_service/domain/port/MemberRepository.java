package com.turkcell.user_service.domain.port;

import com.turkcell.user_service.domain.model.Member;
import com.turkcell.user_service.domain.model.MemberId;
import com.turkcell.user_service.domain.model.MembershipLevel;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);
    Optional<Member> findById(MemberId memberId);
    List<Member> findAll();
    List<Member> findAllPaged(Integer pageIndex, Integer pageSize);
    void deleteById(MemberId memberId);
    void delete(Member member);
    Optional<Member> findByMembershipLevel(MembershipLevel membershipLevel);
}
