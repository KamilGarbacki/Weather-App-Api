package org.kgarbacki.weatherAppApi.dto;

public record ReportSummaryDto(
        float averageWeeklyPressure,
        float maxWeeklyTemperature,
        float minWeeklyTemperature,
        WeeklyPrecipitations weeklyPrecipitation
) {
}
