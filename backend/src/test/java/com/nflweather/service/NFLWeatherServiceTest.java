package com.nflweather.service;

import com.nflweather.model.NFLGame;
import com.nflweather.repository.NFLGameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NFLWeatherServiceTest {

    @Mock
    private NFLGameRepository nflGameRepository;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private NFLWeatherService nflWeatherService;

    private NFLGame testGame;

    @BeforeEach
    void setUp() {
        testGame = NFLGame.builder()
                .gameId("2025_12_BUF_KC")
                .season(2025)
                .week(12)
                .gameType("REG")
                .awayTeam("BUF")
                .homeTeam("KC")
                .stadium("GEHA Field at Arrowhead Stadium")
                .location("Kansas City")
                .latitude(39.0489)
                .longitude(-94.4839)
                .build();
    }

    @Test
    void testGetAllGames() {
        // Arrange
        List<NFLGame> expectedGames = Arrays.asList(testGame);
        when(nflGameRepository.findAll()).thenReturn(expectedGames);

        // Act
        List<NFLGame> actualGames = nflWeatherService.getAllGames();

        // Assert
        assertNotNull(actualGames);
        assertEquals(1, actualGames.size());
        assertEquals(testGame.getGameId(), actualGames.get(0).getGameId());
        verify(nflGameRepository, times(1)).findAll();
    }

    @Test
    void testGetGameById() {
        // Arrange
        String gameId = "2025_12_BUF_KC";
        when(nflGameRepository.findById(gameId)).thenReturn(Optional.of(testGame));

        // Act
        NFLGame actualGame = nflWeatherService.getGameById(gameId);

        // Assert
        assertNotNull(actualGame);
        assertEquals(gameId, actualGame.getGameId());
        assertEquals("BUF", actualGame.getAwayTeam());
        assertEquals("KC", actualGame.getHomeTeam());
        verify(nflGameRepository, times(1)).findById(gameId);
    }

    @Test
    void testGetGameByIdNotFound() {
        // Arrange
        String gameId = "NONEXISTENT";
        when(nflGameRepository.findById(gameId)).thenReturn(Optional.empty());

        // Act
        NFLGame actualGame = nflWeatherService.getGameById(gameId);

        // Assert
        assertNull(actualGame);
        verify(nflGameRepository, times(1)).findById(gameId);
    }

    @Test
    void testGetGamesByWeek() {
        // Arrange
        Integer week = 12;
        List<NFLGame> expectedGames = Arrays.asList(testGame);
        when(nflGameRepository.findByWeek(week)).thenReturn(expectedGames);

        // Act
        List<NFLGame> actualGames = nflWeatherService.getGamesByWeek(week);

        // Assert
        assertNotNull(actualGames);
        assertEquals(1, actualGames.size());
        assertEquals(week, actualGames.get(0).getWeek());
        verify(nflGameRepository, times(1)).findByWeek(week);
    }

    @Test
    void testGetGamesBySeasonAndWeek() {
        // Arrange
        Integer season = 2025;
        Integer week = 12;
        List<NFLGame> expectedGames = Arrays.asList(testGame);
        when(nflGameRepository.findBySeasonAndWeek(season, week)).thenReturn(expectedGames);

        // Act
        List<NFLGame> actualGames = nflWeatherService.getGamesBySeasonAndWeek(season, week);

        // Assert
        assertNotNull(actualGames);
        assertEquals(1, actualGames.size());
        assertEquals(season, actualGames.get(0).getSeason());
        assertEquals(week, actualGames.get(0).getWeek());
        verify(nflGameRepository, times(1)).findBySeasonAndWeek(season, week);
    }

    @Test
    void testGetGamesByTeam() {
        // Arrange
        String team = "KC";
        List<NFLGame> expectedGames = Arrays.asList(testGame);
        when(nflGameRepository.findByHomeTeamOrAwayTeam(team, team)).thenReturn(expectedGames);

        // Act
        List<NFLGame> actualGames = nflWeatherService.getGamesByTeam(team);

        // Assert
        assertNotNull(actualGames);
        assertEquals(1, actualGames.size());
        assertTrue(actualGames.get(0).getHomeTeam().equals(team) || 
                   actualGames.get(0).getAwayTeam().equals(team));
        verify(nflGameRepository, times(1)).findByHomeTeamOrAwayTeam(team, team);
    }
}
