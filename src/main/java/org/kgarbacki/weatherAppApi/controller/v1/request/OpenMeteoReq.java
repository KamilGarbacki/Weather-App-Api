package org.kgarbacki.weatherAppApi.controller.v1.request;

import java.util.List;

public record OpenMeteoReq(
        float latitude,
        float longitude,
        List<String> daily,
        String timezone
) {

}
