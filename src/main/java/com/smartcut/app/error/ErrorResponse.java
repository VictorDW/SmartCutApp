package com.smartcut.app.error;

public record ErrorResponse(
    String localDateTime,
    int status,
    String error,
    String message
) { }
