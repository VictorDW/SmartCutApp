package com.smartcut.app.Error;

import java.time.LocalDateTime;

public record ErrorResponse(
    LocalDateTime localDateTime,
    int status,
    String error,
    String messaje,
    String path
) { }
