package org.kgarbacki.weatherAppApi.service;

import org.kgarbacki.weatherAppApi.controller.v1.request.WeatherReq;
import org.kgarbacki.weatherAppApi.dto.WeatherReportDto;
import org.kgarbacki.weatherAppApi.dto.ReportSummaryDto;

import java.util.List;

public interface WeatherService {
    List<WeatherReportDto> getWeatherReport(WeatherReq req);
    ReportSummaryDto getReportSummary(WeatherReq req);
}
