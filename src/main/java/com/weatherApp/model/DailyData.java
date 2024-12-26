package com.weatherApp.model;

import java.time.LocalDate;
import java.util.List;

public record DailyData(
        List<LocalDate> time,
        List<Integer> weather_code,
        List<Double> temperature_2m_max,
        List<Double> temperature_2m_min,
        List<Double> sunshine_duration,
        List<Double> precipitation_sum
) {
}
