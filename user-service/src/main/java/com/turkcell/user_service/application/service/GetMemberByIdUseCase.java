package com.turkcell.user_service.application.service;

import com.turkcell.user_service.application.dto.response.MemberResponse;
import com.turkcell.user_service.application.exception.MemberNotFoundException;
import com.turkcell.user_service.application.mapper.MemberMapper;
import com.turkcell.user_service.domain.model.Member;
import com.turkcell.user_service.domain.model.MemberId;
import com.turkcell.user_service.domain.port.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetMemberByIdUseCase {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    public GetMemberByIdUseCase(MemberRepository memberRepository, MemberMapper memberMapper) {
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
    }

    public MemberResponse getById(UUID id){
        Member member = memberRepository.findById(new MemberId(id))
                .orElse(null);
        if(member == null){
            throw new MemberNotFoundException("Member not found" + id);
        }
        return memberMapper.toMemberResponse(member);
    }

}
