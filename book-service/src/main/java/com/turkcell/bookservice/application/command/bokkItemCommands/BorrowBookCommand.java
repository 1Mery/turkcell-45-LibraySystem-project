package com.turkcell.bookservice.application.command.bokkItemCommands;

import java.util.UUID;

public record BorrowBookCommand(UUID bookId) {
}
