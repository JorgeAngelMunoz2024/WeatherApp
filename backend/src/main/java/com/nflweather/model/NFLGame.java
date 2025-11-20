package com.nflweather.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "nfl_games")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NFLGame {

    @Id
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
    
    @OneToOne(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private WeatherData weather;
    
    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private java.util.List<HourlyWeather> hourlyWeather;
    
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
}
