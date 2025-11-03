package com.turkcell.user_service.application.service;

import com.turkcell.user_service.application.dto.response.MemberResponse;
import com.turkcell.user_service.application.mapper.MemberMapper;
import com.turkcell.user_service.domain.model.Member;
import com.turkcell.user_service.domain.port.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllMembersUseCase {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    public GetAllMembersUseCase(MemberRepository memberRepository, MemberMapper memberMapper) {
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
    }

    public List<MemberResponse> getAll(){
        List<Member> members = memberRepository.findAll();
        return members.stream()
                .map(memberMapper::toMemberResponse)
                .toList();
    }
}
