package org.kgarbacki.weatherAppApi.apiClient.response;

public record FeignErrorResponse(
        String reason,
        String error
) {
}
