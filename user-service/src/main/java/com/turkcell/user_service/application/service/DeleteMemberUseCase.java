package com.turkcell.user_service.application.service;

import com.turkcell.user_service.application.dto.response.DeletedMemberResponse;
import com.turkcell.user_service.application.exception.MemberNotFoundException;
import com.turkcell.user_service.domain.model.Member;
import com.turkcell.user_service.domain.model.MemberId;
import com.turkcell.user_service.domain.port.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Service
public class DeleteMemberUseCase {

    private final MemberRepository memberRepository;

    public DeleteMemberUseCase(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public DeletedMemberResponse delete(@PathVariable UUID id){
        Member member = memberRepository.findById(new MemberId(id))
                .orElse(null);
        if(member == null){
            throw new MemberNotFoundException("Member not found" + id);
        }
        memberRepository.delete(member);
        return new DeletedMemberResponse(id);
    }
}
