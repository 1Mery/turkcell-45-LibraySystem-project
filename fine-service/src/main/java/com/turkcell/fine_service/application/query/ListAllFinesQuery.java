package com.turkcell.fine_service.application.query;

import jakarta.validation.constraints.Min;

public record ListAllFinesQuery(@Min(0) Integer pageIndex, @Min(1) Integer pageSize) {
}
