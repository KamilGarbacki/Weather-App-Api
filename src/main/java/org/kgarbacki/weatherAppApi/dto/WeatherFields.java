package org.kgarbacki.weatherAppApi.dto;

public enum WeatherFields {
    WEATHER_CODE,
    TEMPERATURE_2M_MAX,
    TEMPERATURE_2M_MIN,
    SUNSHINE_DURATION,
    SURFACE_PRESSURE_MEAN,
    PRECIPITATION_SUM;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}