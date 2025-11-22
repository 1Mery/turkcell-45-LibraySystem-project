package com.turkcell.bookservice.application.query;

import java.util.UUID;

public record GetBookTitleByBookItemIdQuery(UUID bookItemId) {
}
