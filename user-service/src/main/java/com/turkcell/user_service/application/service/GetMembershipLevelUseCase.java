package com.turkcell.user_service.application.service;

import com.turkcell.user_service.application.dto.response.MembershipLevelResponse;
import com.turkcell.user_service.application.exception.MemberNotFoundException;
import com.turkcell.user_service.domain.model.Member;
import com.turkcell.user_service.domain.model.MemberId;
import com.turkcell.user_service.domain.port.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Üyenin membership level'ını sorgulayan use case.
 * Performans için tüm üye bilgilerini çekmek yerine sadece membership level döner.
 */
@Service
public class GetMembershipLevelUseCase {

    private final MemberRepository memberRepository;

    public GetMembershipLevelUseCase(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MembershipLevelResponse getMembershipLevelById(UUID id) {
        Member member = memberRepository.findById(new MemberId(id))
                .orElseThrow(() -> new MemberNotFoundException("Member not found: " + id));
        
        return new MembershipLevelResponse(member.level().name());
    }
}


