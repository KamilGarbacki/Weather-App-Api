package com.weatherApp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportSummary {
    private double averageWeeklyPressure;
    private double averageSunExposure;
    private double maxWeeklyTemp;
    private double minWeeklyTemp;
    private boolean weekWithPrecipitation;

}
