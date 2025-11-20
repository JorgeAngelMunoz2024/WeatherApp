package com.nflweather.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for hourly weather data
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HourlyWeatherData {
    
    private String time;
    private Double temperature;
    private Integer precipitationProbability;
    private Double precipitation;
    private Integer weatherCode;
    private String weatherDescription;
    private Double windSpeed;
    
    public String getWeatherDescription() {
        if (weatherCode == null) {
            return "Unknown";
        }
        return switch (weatherCode) {
            case 0 -> "Clear sky";
            case 1, 2, 3 -> "Partly cloudy";
            case 45, 48 -> "Foggy";
            case 51, 53, 55 -> "Drizzle";
            case 61, 63, 65 -> "Rain";
            case 71, 73, 75 -> "Snow";
            case 77 -> "Snow grains";
            case 80, 81, 82 -> "Rain showers";
            case 85, 86 -> "Snow showers";
            case 95 -> "Thunderstorm";
            case 96, 99 -> "Thunderstorm with hail";
            default -> "Unknown";
        };
    }
}
