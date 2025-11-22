package com.turkcell.fine_service.application.command;

import java.util.UUID;

public record DeleteFineCommand(UUID fineId) {
}
