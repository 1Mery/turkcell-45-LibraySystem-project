package com.turkcell.user_service.application.mapper;

import com.turkcell.user_service.application.dto.response.MemberResponse;
import com.turkcell.user_service.domain.model.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public MemberResponse  toMemberResponse(Member member){
        return new MemberResponse(
                member.id().value(),
                member.firstName(),
                member.lastName(),
                member.email().value(),
                member.username().value(),
                member.phone().value(),
                member.level().name(),
                member.status().name()

        );
    }
}
