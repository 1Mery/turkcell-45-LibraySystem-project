package com.turkcell.loanservice.web.exception;

public record ErrorResponse(
        String code,
        String message
) { }
