package com.nflweather.service;

import com.nflweather.dto.GameWeatherResponse;
import com.nflweather.dto.OpenMeteoResponse;
import com.nflweather.model.NFLGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class WeatherAPIServiceTest {

    private WeatherAPIService weatherAPIService;
    private NFLGame testGame;

    @BeforeEach
    void setUp() {
        weatherAPIService = new WeatherAPIService();
        
        // Create test game
        testGame = NFLGame.builder()
                .gameId("TEST_GAME_001")
                .homeTeam("Dallas Cowboys")
                .awayTeam("Philadelphia Eagles")
                .stadium("AT&T Stadium")
                .location("Arlington, TX")
                .latitude(32.7473)
                .longitude(-97.0945)
                .gameday(LocalDate.of(2025, 11, 24))
                .gametime(LocalTime.of(16, 30))
                .build();
    }

    @Test
    void testGetGameWeather_NullGame() {
        // Act
        GameWeatherResponse result = weatherAPIService.getGameWeather(null);

        // Assert
        assertNull(result);
    }

    @Test
    void testGetGameWeather_MissingCoordinates() {
        // Arrange
        testGame.setLatitude(null);
        testGame.setLongitude(null);

        // Act
        GameWeatherResponse result = weatherAPIService.getGameWeather(testGame);

        // Assert
        assertNull(result);
    }

    @Test
    void testGetGameWeather_MissingGameTime() {
        // Arrange
        testGame.setGametime(null);

        // Act
        GameWeatherResponse result = weatherAPIService.getGameWeather(testGame);

        // Assert
        assertNull(result);
    }

    @Test
    void testGetGameWeather_MissingGameDate() {
        // Arrange
        testGame.setGameday(null);

        // Act
        GameWeatherResponse result = weatherAPIService.getGameWeather(testGame);

        // Assert
        assertNull(result);
    }
}
