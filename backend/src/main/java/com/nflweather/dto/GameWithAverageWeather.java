package com.nflweather.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO for returning game data with average weather for display on cards
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameWithAverageWeather {
    private String gameId;
    private Integer season;
    private Integer week;
    private String gameType;
    private String league; // "NFL" or "CFB"
    private String awayTeam;
    private String homeTeam;
    private LocalDate gameday;
    private LocalTime gametime;
    private String stadium;
    private String location;
    private Double latitude;
    private Double longitude;
    
    // Average weather data
    private Double avgTemperature;
    private Double avgPrecipitationProbability;
    private Double avgWindSpeed;
    private String weatherDescription;
    private Integer weatherCode;
}
