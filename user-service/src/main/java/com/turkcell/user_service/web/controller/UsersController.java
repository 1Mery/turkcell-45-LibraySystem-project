package com.turkcell.user_service.web.controller;

import com.turkcell.user_service.application.dto.request.UpdateMemberRequest;
import com.turkcell.user_service.application.dto.response.DeletedMemberResponse;
import com.turkcell.user_service.application.dto.response.MemberResponse;
import com.turkcell.user_service.application.service.DeleteMemberUseCase;
import com.turkcell.user_service.application.service.GetAllMembersUseCase;
import com.turkcell.user_service.application.service.GetMemberByIdUseCase;
import com.turkcell.user_service.application.service.UpdateMemberUseCase;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {

    private final UpdateMemberUseCase updateMemberUseCase;
    private final DeleteMemberUseCase deleteMemberUseCase;
    private final GetMemberByIdUseCase getMemberByIdUseCase;
    private final GetAllMembersUseCase getAllMembersUseCase;

    public UsersController(UpdateMemberUseCase updateMemberUseCase, DeleteMemberUseCase deleteMemberUseCase, GetMemberByIdUseCase getMemberByIdUseCase, GetAllMembersUseCase getAllMembersUseCase) {
        this.updateMemberUseCase = updateMemberUseCase;
        this.deleteMemberUseCase = deleteMemberUseCase;
        this.getMemberByIdUseCase = getMemberByIdUseCase;
        this.getAllMembersUseCase = getAllMembersUseCase;
    }


    @PutMapping("/{id}")
    public MemberResponse update(@PathVariable UUID id,
                                        @RequestBody @Valid UpdateMemberRequest request){
        return updateMemberUseCase.update(id, request);
    }

    @DeleteMapping("/{id}")
    public DeletedMemberResponse delete(@PathVariable UUID id){
        return deleteMemberUseCase.delete(id);
    }

    @GetMapping("/{id}")
    public MemberResponse getById(@PathVariable UUID id){
        return getMemberByIdUseCase.getById(id);
    }

    @GetMapping
    public List<MemberResponse> getAll(){
        return getAllMembersUseCase.getAll();
    }
}
