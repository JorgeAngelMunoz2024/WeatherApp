package com.nflweather.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "hourly_weather")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HourlyWeather {

    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", referencedColumnName = "gameId")
    @JsonBackReference
    private NFLGame game;

    private String time; // ISO format timestamp
    private Double temperature;
    private Integer precipitationProbability;
    private Double precipitation;
    private Integer weatherCode;
    private String weatherDescription;
    private Double windSpeed;
    
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
