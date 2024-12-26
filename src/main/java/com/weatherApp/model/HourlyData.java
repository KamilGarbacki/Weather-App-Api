package com.weatherApp.model;

import java.util.List;

public record HourlyData(
        List<Double> surface_pressure
) {
}
