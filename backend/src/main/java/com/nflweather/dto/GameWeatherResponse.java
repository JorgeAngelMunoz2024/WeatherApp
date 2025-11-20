package com.nflweather.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Response DTO containing game information and hourly weather data
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameWeatherResponse {
    
    private String gameId;
    private String homeTeam;
    private String awayTeam;
    private String stadium;
    private String location;
    private String gameTime;
    private String gameDate;
    private Double latitude;
    private Double longitude;
    private String timezone;
    
    // List of hourly weather data for the game duration (3 hours)
    private List<HourlyWeatherData> hourlyWeather;
}
