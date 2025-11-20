package com.nflweather.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WeatherDataTest {

    @Test
    void testWeatherDataBuilder() {
        // Arrange & Act
        WeatherData weather = WeatherData.builder()
                .avgTemperature(45.2)
                .avgPrecipitationProbability(20)
                .avgPrecipitation(0.0)
                .weatherCode(2)
                .avgWindSpeed(12.5)
                .timezone("America/Chicago")
                .build();

        // Assert
        assertNotNull(weather);
        assertEquals(45.2, weather.getAvgTemperature());
        assertEquals(20, weather.getAvgPrecipitationProbability());
        assertEquals(0.0, weather.getAvgPrecipitation());
        assertEquals(2, weather.getWeatherCode());
        assertEquals(12.5, weather.getAvgWindSpeed());
    }

    @Test
    void testGetWeatherDescription() {
        // Test various weather codes
        WeatherData clearSky = WeatherData.builder().weatherCode(0).build();
        assertEquals("Clear sky", clearSky.getWeatherDescription());

        WeatherData partlyCloudy = WeatherData.builder().weatherCode(2).build();
        assertEquals("Partly cloudy", partlyCloudy.getWeatherDescription());

        WeatherData rain = WeatherData.builder().weatherCode(61).build();
        assertEquals("Rain", rain.getWeatherDescription());

        WeatherData snow = WeatherData.builder().weatherCode(71).build();
        assertEquals("Snow", snow.getWeatherDescription());

        WeatherData thunderstorm = WeatherData.builder().weatherCode(95).build();
        assertEquals("Thunderstorm", thunderstorm.getWeatherDescription());

        WeatherData unknown = WeatherData.builder().weatherCode(999).build();
        assertEquals("Unknown", unknown.getWeatherDescription());
    }

    @Test
    void testGameRelationship() {
        // Arrange
        NFLGame game = NFLGame.builder()
                .gameId("2025_12_TEST")
                .build();

        WeatherData weather = WeatherData.builder()
                .game(game)
                .avgTemperature(50.0)
                .build();

        // Assert
        assertNotNull(weather.getGame());
        assertEquals("2025_12_TEST", weather.getGame().getGameId());
    }
}
