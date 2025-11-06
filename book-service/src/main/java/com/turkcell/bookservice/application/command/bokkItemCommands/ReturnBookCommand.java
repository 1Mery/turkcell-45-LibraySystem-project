package com.turkcell.bookservice.application.command.bokkItemCommands;

import java.util.UUID;

public record ReturnBookCommand(UUID bookId, UUID itemId) {
}
