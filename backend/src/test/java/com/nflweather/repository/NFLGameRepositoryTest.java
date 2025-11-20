package com.nflweather.repository;

import com.nflweather.model.NFLGame;
import com.nflweather.model.WeatherData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class NFLGameRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private NFLGameRepository nflGameRepository;

    private NFLGame testGame1;
    private NFLGame testGame2;

    @BeforeEach
    void setUp() {
        testGame1 = NFLGame.builder()
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

        WeatherData weather1 = WeatherData.builder()
                .game(testGame1)
                .avgTemperature(45.2)
                .avgPrecipitationProbability(20)
                .avgPrecipitation(0.0)
                .weatherCode(2)
                .avgWindSpeed(12.5)
                .timezone("America/Chicago")
                .build();

        testGame1.setWeather(weather1);

        testGame2 = NFLGame.builder()
                .gameId("2025_12_SF_GB")
                .season(2025)
                .week(12)
                .gameType("REG")
                .awayTeam("SF")
                .homeTeam("GB")
                .gameday(LocalDate.of(2025, 11, 24))
                .gametime(LocalTime.of(13, 0))
                .stadium("Lambeau Field")
                .location("Green Bay")
                .latitude(44.5013)
                .longitude(-88.0622)
                .build();

        entityManager.persist(testGame1);
        entityManager.persist(testGame2);
        entityManager.flush();
    }

    @Test
    void testFindById() {
        // Act
        Optional<NFLGame> found = nflGameRepository.findById("2025_12_BUF_KC");

        // Assert
        assertTrue(found.isPresent());
        assertEquals("BUF", found.get().getAwayTeam());
        assertEquals("KC", found.get().getHomeTeam());
    }

    @Test
    void testFindByWeek() {
        // Act
        List<NFLGame> games = nflGameRepository.findByWeek(12);

        // Assert
        assertNotNull(games);
        assertEquals(2, games.size());
    }

    @Test
    void testFindBySeason() {
        // Act
        List<NFLGame> games = nflGameRepository.findBySeason(2025);

        // Assert
        assertNotNull(games);
        assertEquals(2, games.size());
    }

    @Test
    void testFindBySeasonAndWeek() {
        // Act
        List<NFLGame> games = nflGameRepository.findBySeasonAndWeek(2025, 12);

        // Assert
        assertNotNull(games);
        assertEquals(2, games.size());
    }

    @Test
    void testFindByHomeTeamOrAwayTeam() {
        // Act
        List<NFLGame> kcGames = nflGameRepository.findByHomeTeamOrAwayTeam("KC", "KC");
        List<NFLGame> sfGames = nflGameRepository.findByHomeTeamOrAwayTeam("SF", "SF");

        // Assert
        assertEquals(1, kcGames.size());
        assertEquals("KC", kcGames.get(0).getHomeTeam());
        
        assertEquals(1, sfGames.size());
        assertEquals("SF", sfGames.get(0).getAwayTeam());
    }

    @Test
    void testFindByGamedayBetween() {
        // Arrange
        LocalDate startDate = LocalDate.of(2025, 11, 20);
        LocalDate endDate = LocalDate.of(2025, 11, 30);

        // Act
        List<NFLGame> games = nflGameRepository.findByGamedayBetween(startDate, endDate);

        // Assert
        assertNotNull(games);
        assertEquals(2, games.size());
    }

    @Test
    void testSaveGameWithWeather() {
        // Arrange
        NFLGame newGame = NFLGame.builder()
                .gameId("2025_12_DAL_NYG")
                .season(2025)
                .week(12)
                .gameType("REG")
                .awayTeam("DAL")
                .homeTeam("NYG")
                .gameday(LocalDate.of(2025, 11, 28))
                .stadium("MetLife Stadium")
                .location("East Rutherford")
                .build();

        WeatherData weather = WeatherData.builder()
                .game(newGame)
                .avgTemperature(38.5)
                .avgPrecipitationProbability(10)
                .avgPrecipitation(0.0)
                .weatherCode(1)
                .avgWindSpeed(8.3)
                .build();

        newGame.setWeather(weather);

        // Act
        NFLGame saved = nflGameRepository.save(newGame);

        // Assert
        assertNotNull(saved);
        assertNotNull(saved.getWeather());
        assertEquals(38.5, saved.getWeather().getAvgTemperature());
    }
}
