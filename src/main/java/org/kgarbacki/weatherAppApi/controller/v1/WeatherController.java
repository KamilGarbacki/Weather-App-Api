package org.kgarbacki.weatherAppApi.controller.v1;

import lombok.RequiredArgsConstructor;
import org.kgarbacki.weatherAppApi.controller.v1.request.WeatherReq;
import org.kgarbacki.weatherAppApi.dto.WeatherReportDto;
import org.kgarbacki.weatherAppApi.dto.ReportSummaryDto;
import org.kgarbacki.weatherAppApi.service.WeatherService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/weather")
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/report")
    public ResponseEntity<List<WeatherReportDto>> getWeatherReport(@Validated @ParameterObject WeatherReq req) {
        return ResponseEntity.ok(weatherService.getWeatherReport(req));
    }

    @GetMapping("/summary")
    public ResponseEntity<ReportSummaryDto> getReportSummary(@Validated @ParameterObject WeatherReq req) {
        return ResponseEntity.ok(weatherService.getReportSummary(req));
    }
}
