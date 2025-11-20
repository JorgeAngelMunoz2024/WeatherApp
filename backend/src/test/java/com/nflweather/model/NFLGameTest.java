package com.nflweather.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class NFLGameTest {

    @Test
    void testNFLGameBuilder() {
        // Arrange & Act
        NFLGame game = NFLGame.builder()
                .gameId("2025_12_BUF_KC")
                .season(2025)
                .week(12)
                .gameType("REG")
                .awayTeam("BUF")
                .homeTeam("KC")
                .gameday(LocalDate.of(2025, 11, 24))
                .gametime(LocalTime.of(16, 25))
                .stadium("GEHA Field at Arrowhead Stadium")
                .location("Kansas City")
                .latitude(39.0489)
                .longitude(-94.4839)
                .build();

        // Assert
        assertNotNull(game);
        assertEquals("2025_12_BUF_KC", game.getGameId());
        assertEquals(2025, game.getSeason());
        assertEquals(12, game.getWeek());
        assertEquals("BUF", game.getAwayTeam());
        assertEquals("KC", game.getHomeTeam());
        assertEquals("Kansas City", game.getLocation());
    }

    @Test
    void testWeatherDataRelationship() {
        // Arrange
        NFLGame game = NFLGame.builder()
                .gameId("2025_12_TEST")
                .season(2025)
                .week(12)
                .build();

        WeatherData weather = WeatherData.builder()
                .game(game)
                .avgTemperature(45.2)
                .avgPrecipitationProbability(20)
                .avgPrecipitation(0.0)
                .weatherCode(2)
                .avgWindSpeed(12.5)
                .build();

        game.setWeather(weather);

        // Assert
        assertNotNull(game.getWeather());
        assertEquals(45.2, game.getWeather().getAvgTemperature());
        assertEquals(game, game.getWeather().getGame());
    }
}
