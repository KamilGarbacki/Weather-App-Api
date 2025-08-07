package org.kgarbacki.weatherAppApi.util;

import org.kgarbacki.weatherAppApi.dto.WeeklyPrecipitations;

import java.util.List;

public class WeatherUtil {
    public static float calcMaxWeeklyTemperature(List<Float> dailyMaxTemperatures){
        dailyMaxTemperatures.sort(Float::compareTo);
        return dailyMaxTemperatures.getLast();
    }

    public static float calcMinWeeklyTemperature(List<Float> dailyMinTemperatures){
        dailyMinTemperatures.sort(Float::compareTo);
        return dailyMinTemperatures.getFirst();
    }

    public static float calcAveragePressure(List<Float> dailyPressures){
        float sum = dailyPressures.stream().reduce(
                0.f,
                Float::sum
        );

        return sum / dailyPressures.size();
    }

    public static WeeklyPrecipitations evaluateWeeklyPrecipitation(List<Float> dailyPrecipitations){
        int daysWithPrecipitations = (int) dailyPrecipitations.stream()
                .filter(p -> p > 0)
                .count();

        return daysWithPrecipitations > 3 ? WeeklyPrecipitations.WEEK_WITH_PRECIPITATIONS : WeeklyPrecipitations.WEEK_WITHOUT_PRECIPITATIONS;
    }
}
