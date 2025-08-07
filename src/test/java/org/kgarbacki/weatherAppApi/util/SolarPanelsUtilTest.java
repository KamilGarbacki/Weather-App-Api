package org.kgarbacki.weatherAppApi.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SolarPanelsUtilTest {

    @Test
    void calcGeneratedEnergy() {
        //Given
        float sunlightExposure = 7200f;
        float expectedValue = 1.0f;

        //When
        float generatedEnergy = SolarPanelsUtil.calcGeneratedEnergy(sunlightExposure);

        //Then
        assertThat(generatedEnergy).isEqualTo(expectedValue);
    }
}
