package org.kgarbacki.weatherAppApi.dto;

import java.time.LocalDateTime;

public record ErrorMessageDto(
        String path,
        String message,
        int statusCode,
        LocalDateTime localDateTime
) {
}
