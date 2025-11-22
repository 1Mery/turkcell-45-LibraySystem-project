package com.turkcell.bookservice.application.command.bookItemCommands;

import java.util.UUID;

public record AddBookCopyCommand(UUID bookId) {
}
