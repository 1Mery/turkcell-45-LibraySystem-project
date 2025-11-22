package com.turkcell.bookservice.application.command.bookItemCommands;

import java.util.UUID;

public record ReturnBookCommand(UUID bookId, UUID itemId) {
}
