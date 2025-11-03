package com.turkcell.user_service.application.service;

import com.turkcell.user_service.application.dto.request.UpdateMemberRequest;
import com.turkcell.user_service.application.dto.response.MemberResponse;
import com.turkcell.user_service.application.exception.MemberNotFoundException;
import com.turkcell.user_service.application.mapper.MemberMapper;
import com.turkcell.user_service.domain.event.EmailChangedEvent;
import com.turkcell.user_service.domain.model.Email;
import com.turkcell.user_service.domain.model.Member;
import com.turkcell.user_service.domain.model.MemberId;
import com.turkcell.user_service.domain.port.DomainEventPublisher;
import com.turkcell.user_service.domain.port.MemberRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Service
@Validated
public class UpdateMemberUseCase {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final DomainEventPublisher eventPublisher;

    public UpdateMemberUseCase(MemberRepository memberRepository, MemberMapper memberMapper, DomainEventPublisher eventPublisher) {
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
        this.eventPublisher = eventPublisher;
    }

    public MemberResponse update(UUID id, @Valid UpdateMemberRequest request){

        Member member = memberRepository.findById(new MemberId(id))
                .orElseThrow(() ->
                        new MemberNotFoundException("Member not found" + id));
        member.updateEmail(new Email(request.email()));
        member = memberRepository.save(member);

        EmailChangedEvent event = new EmailChangedEvent(new MemberId(id), new Email(request.email()));
        eventPublisher.publish(event);

        return memberMapper.toMemberResponse(member);
    }
}
