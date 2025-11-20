package com.nflweather.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "weather_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherData {

    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name = "game_id", referencedColumnName = "gameId")
    @JsonBackReference
    private NFLGame game;

    // Average weather values for the game duration
    private Double avgTemperature;
    private Integer avgPrecipitationProbability;
    private Double avgPrecipitation;
    private Double avgWindSpeed;
    private Integer weatherCode;
    private String weatherDescription;
    
    private String timezone;
    
    @Column(updatable = false)
    private String createdAt;
    
    private String updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = java.time.LocalDateTime.now().toString();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = java.time.LocalDateTime.now().toString();
    }
    
    // Helper method to get weather description from code
    public String getWeatherDescription() {
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
