package com.smartcut.app.Error;

public record ErrorResponse(
    String localDateTime,
    int status,
    String error,
    String message
) { }
