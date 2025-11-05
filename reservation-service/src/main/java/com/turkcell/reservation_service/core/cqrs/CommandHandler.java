package com.turkcell.reservation_service.core.cqrs;

public interface CommandHandler<C extends Command<R>, R> {

    R handle(C command);
}
