package com.turkcell.loanservice.application.query;

import java.util.UUID;

public record GetLoanHistoryQuery(
        UUID userId
) {
}
