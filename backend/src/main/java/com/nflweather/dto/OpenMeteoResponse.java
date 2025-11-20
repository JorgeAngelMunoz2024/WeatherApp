package com.nflweather.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for Open-Meteo API response
 */
@Data
@NoArgsConstructor
public class OpenMeteoResponse {
    
    private Double latitude;
    private Double longitude;
    
    @JsonProperty("generationtime_ms")
    private Double generationtimeMs;
    
    @JsonProperty("utc_offset_seconds")
    private Integer utcOffsetSeconds;
    
    private String timezone;
    
    @JsonProperty("timezone_abbreviation")
    private String timezoneAbbreviation;
    
    private Double elevation;
    
    @JsonProperty("hourly_units")
    private HourlyUnits hourlyUnits;
    
    private Hourly hourly;
    
    @Data
    @NoArgsConstructor
    public static class HourlyUnits {
        private String time;
        
        @JsonProperty("temperature_2m")
        private String temperature2m;
        
        @JsonProperty("precipitation_probability")
        private String precipitationProbability;
        
        private String precipitation;
        
        @JsonProperty("weather_code")
        private String weatherCode;
        
        @JsonProperty("wind_speed_10m")
        private String windSpeed10m;
    }
    
    @Data
    @NoArgsConstructor
    public static class Hourly {
        private List<String> time;
        
        @JsonProperty("temperature_2m")
        private List<Double> temperature2m;
        
        @JsonProperty("precipitation_probability")
        private List<Integer> precipitationProbability;
        
        private List<Double> precipitation;
        
        @JsonProperty("weather_code")
        private List<Integer> weatherCode;
        
        @JsonProperty("wind_speed_10m")
        private List<Double> windSpeed10m;
    }
}
