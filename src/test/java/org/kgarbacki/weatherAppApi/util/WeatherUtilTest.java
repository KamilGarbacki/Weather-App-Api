package org.kgarbacki.weatherAppApi.util;

import org.junit.jupiter.api.Test;
import org.kgarbacki.weatherAppApi.dto.WeeklyPrecipitations;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class WeatherUtilTest {

    @Test
    void calcMaxWeeklyTemperature() {
        //Given
        List<Float> temps = new ArrayList<>(List.of(18.5f, 22.3f, 19.0f, 24.1f, 21.0f));
        float expectedValue = 24.1f;

        //When
        float result = WeatherUtil.calcMaxWeeklyTemperature(temps);
        assertThat(result).isEqualTo(expectedValue);
    }

    @Test
    void calcMinWeeklyTemperature() {
        //Given
        List<Float> temps = new ArrayList<>(List.of(18.5f, 22.3f, 19.0f, 24.1f, 21.0f));
        float expectedValue = 18.5f;

        //When
        float result = WeatherUtil.calcMinWeeklyTemperature(temps);

        //Then
        assertThat(result).isEqualTo(expectedValue);
    }

    @Test
    void calcAveragePressure() {
        /// Given
        List<Float> pressures = new ArrayList<>(List.of(1012.0f, 1010.0f, 1011.0f, 1013.0f));
        float expectedValue = 1011.5f;

        //When
        float result = WeatherUtil.calcAveragePressure(pressures);

        //Then
        assertThat(result).isEqualTo(expectedValue);
    }

    @Test
    void evaluateWeeklyPrecipitationReturnsWeekWithPrecipitations() {
        //Given
        List<Float> precipitations = new ArrayList<>(List.of(0f, 1.2f, 0.5f, 2.0f, 0f, 0.1f, 0f));

        //When
        var result = WeatherUtil.evaluateWeeklyPrecipitation(precipitations);

        //Then
        assertThat(result).isEqualTo(WeeklyPrecipitations.WEEK_WITH_PRECIPITATIONS);
    }

    @Test
    void evaluateWeeklyPrecipitationReturnsWeekWithoutPrecipitations() {
        //Given
        List<Float> precipitations = new ArrayList<>(List.of(0f, 0f, 0.5f, 2.0f, 0f, 0.1f, 0f));

        //When
        var result = WeatherUtil.evaluateWeeklyPrecipitation(precipitations);

        //Then
        assertThat(result).isEqualTo(WeeklyPrecipitations.WEEK_WITHOUT_PRECIPITATIONS);
    }
}