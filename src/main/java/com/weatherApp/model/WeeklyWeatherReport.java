package com.weatherApp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyWeatherReport {
    private List<LocalDate> dates;
    private List<Integer> weatherCodes;
    private List<Double> maxTemps;
    private List<Double> minTemps;
    private List<Double> generatedEnergies;
}
