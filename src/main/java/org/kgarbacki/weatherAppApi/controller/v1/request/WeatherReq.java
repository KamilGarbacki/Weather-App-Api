package org.kgarbacki.weatherAppApi.controller.v1.request;

import org.hibernate.validator.constraints.Range;

public record WeatherReq(
        @Range(min=-90, max=90, message="Latitude must be in the range from -90 to 90.")
        float latitude,
        @Range(min=-180, max=180, message="Longitude must be in the range from -180 to 180.")
        float longitude
) {
}
