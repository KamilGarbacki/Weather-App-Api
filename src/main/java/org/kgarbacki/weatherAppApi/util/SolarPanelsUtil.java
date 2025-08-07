package org.kgarbacki.weatherAppApi.util;

public class SolarPanelsUtil {
    private static final float PANELS_POWER = 2.5F;
    private static final float PANELS_EFFICIENCY = 0.2F;
    private static final int SECONDS_IN_HOUR = 3600;

    public static float calcGeneratedEnergy(float sunlightExposure){
        return sunlightExposure / SECONDS_IN_HOUR * PANELS_POWER * PANELS_EFFICIENCY;
    }
}
