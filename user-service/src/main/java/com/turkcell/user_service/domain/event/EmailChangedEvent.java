package com.turkcell.user_service.domain.event;

import com.turkcell.user_service.domain.model.Email;
import com.turkcell.user_service.domain.model.MemberId;

public record EmailChangedEvent(MemberId id, Email newEmail) {
}
