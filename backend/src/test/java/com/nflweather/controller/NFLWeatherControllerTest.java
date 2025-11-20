package com.nflweather.controller;

import com.nflweather.dto.GameWeatherResponse;
import com.nflweather.dto.HourlyWeatherData;
import com.nflweather.model.NFLGame;
import com.nflweather.model.WeatherData;
import com.nflweather.service.NFLWeatherService;
import com.nflweather.service.WeatherAPIService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NFLWeatherController.class)
class NFLWeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NFLWeatherService nflWeatherService;

    @MockBean
    private WeatherAPIService weatherAPIService;

    private NFLGame testGame;
    private GameWeatherResponse testWeatherResponse;

    @BeforeEach
    void setUp() {
        testGame = NFLGame.builder()
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

        WeatherData weather = WeatherData.builder()
                .game(testGame)
                .avgTemperature(45.2)
                .avgPrecipitationProbability(20)
                .avgPrecipitation(0.0)
                .weatherCode(2)
                .avgWindSpeed(12.5)
                .weatherDescription("Partly cloudy")
                .timezone("America/Chicago")
                .build();

        testGame.setWeather(weather);

        // Create test weather response for new endpoint
        HourlyWeatherData hour1 = HourlyWeatherData.builder()
                .time("2025-11-24T16:00:00-06:00")
                .temperature(45.2)
                .precipitationProbability(20)
                .precipitation(0.0)
                .weatherCode(2)
                .weatherDescription("Partly cloudy")
                .windSpeed(12.5)
                .build();

        HourlyWeatherData hour2 = HourlyWeatherData.builder()
                .time("2025-11-24T17:00:00-06:00")
                .temperature(44.1)
                .precipitationProbability(25)
                .precipitation(0.0)
                .weatherCode(2)
                .weatherDescription("Partly cloudy")
                .windSpeed(13.2)
                .build();

        HourlyWeatherData hour3 = HourlyWeatherData.builder()
                .time("2025-11-24T18:00:00-06:00")
                .temperature(42.8)
                .precipitationProbability(30)
                .precipitation(0.0)
                .weatherCode(3)
                .weatherDescription("Partly cloudy")
                .windSpeed(14.1)
                .build();

        testWeatherResponse = GameWeatherResponse.builder()
                .gameId("2025_12_BUF_KC")
                .homeTeam("KC")
                .awayTeam("BUF")
                .stadium("GEHA Field at Arrowhead Stadium")
                .location("Kansas City")
                .latitude(39.0489)
                .longitude(-94.4839)
                .timezone("America/Chicago")
                .gameDate("2025-11-24")
                .gameTime("16:25:00")
                .hourlyWeather(Arrays.asList(hour1, hour2, hour3))
                .build();
    }

    @Test
    void testGetAllGames() throws Exception {
        // Arrange
        List<NFLGame> games = Arrays.asList(testGame);
        when(nflWeatherService.getAllGames()).thenReturn(games);

        // Act & Assert
        mockMvc.perform(get("/api/nfl/games")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].gameId").value("2025_12_BUF_KC"))
                .andExpect(jsonPath("$[0].awayTeam").value("BUF"))
                .andExpect(jsonPath("$[0].homeTeam").value("KC"));

        verify(nflWeatherService, times(1)).getAllGames();
    }

    @Test
    void testGetGameById() throws Exception {
        // Arrange
        String gameId = "2025_12_BUF_KC";
        when(nflWeatherService.getGameById(gameId)).thenReturn(testGame);

        // Act & Assert
        mockMvc.perform(get("/api/nfl/games/{gameId}", gameId)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameId").value(gameId))
                .andExpect(jsonPath("$.stadium").value("GEHA Field at Arrowhead Stadium"));

        verify(nflWeatherService, times(1)).getGameById(gameId);
    }

    @Test
    void testGetGameByIdNotFound() throws Exception {
        // Arrange
        String gameId = "NONEXISTENT";
        when(nflWeatherService.getGameById(gameId)).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/api/nfl/games/{gameId}", gameId)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());

        verify(nflWeatherService, times(1)).getGameById(gameId);
    }

    @Test
    void testGetGamesByWeek() throws Exception {
        // Arrange
        Integer week = 12;
        List<NFLGame> games = Arrays.asList(testGame);
        when(nflWeatherService.getGamesByWeek(week)).thenReturn(games);

        // Act & Assert
        mockMvc.perform(get("/api/nfl/games/week/{week}", week)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].week").value(12));

        verify(nflWeatherService, times(1)).getGamesByWeek(week);
    }

    @Test
    void testGetGamesBySeasonAndWeek() throws Exception {
        // Arrange
        Integer season = 2025;
        Integer week = 12;
        List<NFLGame> games = Arrays.asList(testGame);
        when(nflWeatherService.getGamesBySeasonAndWeek(season, week)).thenReturn(games);

        // Act & Assert
        mockMvc.perform(get("/api/nfl/games/season/{season}/week/{week}", season, week)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].season").value(2025))
                .andExpect(jsonPath("$[0].week").value(12));

        verify(nflWeatherService, times(1)).getGamesBySeasonAndWeek(season, week);
    }

    @Test
    void testGetGamesByTeam() throws Exception {
        // Arrange
        String team = "KC";
        List<NFLGame> games = Arrays.asList(testGame);
        when(nflWeatherService.getGamesByTeam(team)).thenReturn(games);

        // Act & Assert
        mockMvc.perform(get("/api/nfl/games/team/{team}", team)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].homeTeam").value("KC"));

        verify(nflWeatherService, times(1)).getGamesByTeam(team);
    }

    @Test
    void testRefreshData() throws Exception {
        // Arrange
        doNothing().when(nflWeatherService).refreshGameData();

        // Act & Assert
        mockMvc.perform(post("/api/nfl/refresh")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Game data refreshed successfully"));

        verify(nflWeatherService, times(1)).refreshGameData();
    }

    @Test
    void testRefreshDataError() throws Exception {
        // Arrange
        doThrow(new RuntimeException("Test error")).when(nflWeatherService).refreshGameData();

        // Act & Assert
        mockMvc.perform(post("/api/nfl/refresh")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.message").exists());

        verify(nflWeatherService, times(1)).refreshGameData();
    }

    @Test
    void testHealthCheck() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/nfl/health")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.service").value("NFL Weather Backend"));
    }

    @Test
    void testGetGameWeather_Success() throws Exception {
        // Arrange
        when(nflWeatherService.getGameById("2025_12_BUF_KC")).thenReturn(testGame);
        when(weatherAPIService.getGameWeather(testGame)).thenReturn(testWeatherResponse);

        // Act & Assert
        mockMvc.perform(get("/api/nfl/games/2025_12_BUF_KC/weather")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.gameId").value("2025_12_BUF_KC"))
                .andExpect(jsonPath("$.homeTeam").value("KC"))
                .andExpect(jsonPath("$.awayTeam").value("BUF"))
                .andExpect(jsonPath("$.stadium").value("GEHA Field at Arrowhead Stadium"))
                .andExpect(jsonPath("$.location").value("Kansas City"))
                .andExpect(jsonPath("$.hourlyWeather").isArray())
                .andExpect(jsonPath("$.hourlyWeather.length()").value(3))
                .andExpect(jsonPath("$.hourlyWeather[0].temperature").value(45.2))
                .andExpect(jsonPath("$.hourlyWeather[0].weatherDescription").value("Partly cloudy"));

        verify(nflWeatherService, times(1)).getGameById("2025_12_BUF_KC");
        verify(weatherAPIService, times(1)).getGameWeather(testGame);
    }

    @Test
    void testGetGameWeather_GameNotFound() throws Exception {
        // Arrange
        when(nflWeatherService.getGameById("INVALID_ID")).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/api/nfl/games/INVALID_ID/weather")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(nflWeatherService, times(1)).getGameById("INVALID_ID");
        verify(weatherAPIService, never()).getGameWeather(any());
    }

    @Test
    void testGetGameWeather_WeatherServiceReturnsNull() throws Exception {
        // Arrange
        when(nflWeatherService.getGameById("2025_12_BUF_KC")).thenReturn(testGame);
        when(weatherAPIService.getGameWeather(testGame)).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/api/nfl/games/2025_12_BUF_KC/weather")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());

        verify(nflWeatherService, times(1)).getGameById("2025_12_BUF_KC");
        verify(weatherAPIService, times(1)).getGameWeather(testGame);
    }

    @Test
    void testGetGameWeather_EmptyHourlyWeather() throws Exception {
        // Arrange
        testWeatherResponse.setHourlyWeather(Collections.emptyList());
        when(nflWeatherService.getGameById("2025_12_BUF_KC")).thenReturn(testGame);
        when(weatherAPIService.getGameWeather(testGame)).thenReturn(testWeatherResponse);

        // Act & Assert
        mockMvc.perform(get("/api/nfl/games/2025_12_BUF_KC/weather")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hourlyWeather").isArray())
                .andExpect(jsonPath("$.hourlyWeather.length()").value(0));

        verify(nflWeatherService, times(1)).getGameById("2025_12_BUF_KC");
        verify(weatherAPIService, times(1)).getGameWeather(testGame);
    }
}
