package com.nflweather.controller;

import com.nflweather.dto.GameWeatherResponse;
import com.nflweather.dto.GameWithAverageWeather;
import com.nflweather.dto.HourlyWeatherData;
import com.nflweather.model.HourlyWeather;
import com.nflweather.model.NFLGame;
import com.nflweather.model.WeatherData;
import com.nflweather.repository.HourlyWeatherRepository;
import com.nflweather.repository.WeatherDataRepository;
import com.nflweather.service.NFLWeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/nfl")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "${cors.allowed.origins}")
public class NFLWeatherController {

    private final NFLWeatherService nflWeatherService;
    private final WeatherDataRepository weatherDataRepository;
    private final HourlyWeatherRepository hourlyWeatherRepository;

    /**
     * Get all games with average weather data from database
     */
    @GetMapping("/games")
    public ResponseEntity<List<GameWithAverageWeather>> getAllGames(
            @RequestParam(required = false) String league) {
        log.info("GET /api/nfl/games - Fetching games with cached weather data (league: {})", league);
        List<NFLGame> games;
        if (league != null && !league.isEmpty()) {
            games = nflWeatherService.getGamesByLeague(league);
        } else {
            games = nflWeatherService.getAllGames();
        }
        List<GameWithAverageWeather> gamesWithWeather = games.stream()
                .map(this::buildGameWithAverageWeather)
                .collect(Collectors.toList());
        return ResponseEntity.ok(gamesWithWeather);
    }
    
    /**
     * Build GameWithAverageWeather from cached data in database
     */
    private GameWithAverageWeather buildGameWithAverageWeather(NFLGame game) {
        GameWithAverageWeather result = GameWithAverageWeather.builder()
                .gameId(game.getGameId())
                .season(game.getSeason())
                .week(game.getWeek())
                .gameType(game.getGameType())
                .league(game.getLeague())
                .awayTeam(game.getAwayTeam())
                .homeTeam(game.getHomeTeam())
                .gameday(game.getGameday())
                .gametime(game.getGametime())
                .stadium(game.getStadium())
                .location(game.getLocation())
                .latitude(game.getLatitude())
                .longitude(game.getLongitude())
                .build();
        
        try {
            // Get cached weather data from database
            Optional<WeatherData> weatherDataOpt = weatherDataRepository.findByGame_GameId(game.getGameId());
            
            if (weatherDataOpt.isPresent()) {
                WeatherData weatherData = weatherDataOpt.get();
                result.setAvgTemperature(weatherData.getAvgTemperature());
                result.setAvgPrecipitationProbability(weatherData.getAvgPrecipitationProbability() != null ? 
                    weatherData.getAvgPrecipitationProbability().doubleValue() : null);
                result.setAvgWindSpeed(weatherData.getAvgWindSpeed());
                result.setWeatherDescription(weatherData.getWeatherDescription());
                result.setWeatherCode(weatherData.getWeatherCode());
                
                log.debug("Loaded cached weather for game: {}", game.getGameId());
            } else {
                log.warn("No cached weather data found for game: {}", game.getGameId());
            }
        } catch (Exception e) {
            log.error("Error loading cached weather for game {}: {}", game.getGameId(), e.getMessage());
        }
        
        return result;
    }

    /**
     * Get game by ID
     */
    @GetMapping("/games/{gameId}")
    public ResponseEntity<NFLGame> getGameById(@PathVariable String gameId) {
        log.info("GET /api/nfl/games/{} - Fetching game by ID", gameId);
        NFLGame game = nflWeatherService.getGameById(gameId);
        if (game == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(game);
    }

    /**
     * Get games by week
     */
    @GetMapping("/games/week/{week}")
    public ResponseEntity<List<NFLGame>> getGamesByWeek(@PathVariable Integer week) {
        log.info("GET /api/nfl/games/week/{} - Fetching games by week", week);
        List<NFLGame> games = nflWeatherService.getGamesByWeek(week);
        return ResponseEntity.ok(games);
    }

    /**
     * Get games by season and week
     */
    @GetMapping("/games/season/{season}/week/{week}")
    public ResponseEntity<List<NFLGame>> getGamesBySeasonAndWeek(
            @PathVariable Integer season,
            @PathVariable Integer week) {
        log.info("GET /api/nfl/games/season/{}/week/{} - Fetching games", season, week);
        List<NFLGame> games = nflWeatherService.getGamesBySeasonAndWeek(season, week);
        return ResponseEntity.ok(games);
    }

    /**
     * Get games by team
     */
    @GetMapping("/games/team/{team}")
    public ResponseEntity<List<NFLGame>> getGamesByTeam(@PathVariable String team) {
        log.info("GET /api/nfl/games/team/{} - Fetching games for team", team);
        List<NFLGame> games = nflWeatherService.getGamesByTeam(team);
        return ResponseEntity.ok(games);
    }

    /**
     * Get detailed hourly weather for a specific game (3-hour duration) from database cache
     */
    @GetMapping("/games/{gameId}/weather")
    public ResponseEntity<GameWeatherResponse> getGameWeather(@PathVariable String gameId) {
        log.info("GET /api/nfl/games/{}/weather - Fetching detailed weather from cache", gameId);
        
        // Get the game
        NFLGame game = nflWeatherService.getGameById(gameId);
        if (game == null) {
            log.warn("Game not found: {}", gameId);
            return ResponseEntity.notFound().build();
        }
        
        try {
            // Get cached hourly weather from database
            List<HourlyWeather> cachedHourlyWeather = hourlyWeatherRepository.findByGame_GameId(gameId);
            
            if (cachedHourlyWeather.isEmpty()) {
                log.warn("No cached hourly weather data found for game: {}", gameId);
                return ResponseEntity.notFound().build();
            }
            
            // Get average weather data for timezone info
            Optional<WeatherData> weatherDataOpt = weatherDataRepository.findByGame_GameId(gameId);
            String timezone = weatherDataOpt.map(WeatherData::getTimezone).orElse("UTC");
            
            // Convert cached data to DTOs
            List<HourlyWeatherData> hourlyWeatherDataList = cachedHourlyWeather.stream()
                .map(hw -> HourlyWeatherData.builder()
                    .time(hw.getTime())
                    .temperature(hw.getTemperature())
                    .precipitationProbability(hw.getPrecipitationProbability())
                    .precipitation(hw.getPrecipitation())
                    .weatherCode(hw.getWeatherCode())
                    .windSpeed(hw.getWindSpeed())
                    .build())
                .collect(Collectors.toList());
            
            // Build response
            GameWeatherResponse weatherResponse = GameWeatherResponse.builder()
                .gameId(game.getGameId())
                .homeTeam(game.getHomeTeam())
                .awayTeam(game.getAwayTeam())
                .stadium(game.getStadium())
                .location(game.getLocation())
                .gameTime(game.getGametime() != null ? game.getGametime().toString() : null)
                .gameDate(game.getGameday() != null ? game.getGameday().toString() : null)
                .latitude(game.getLatitude())
                .longitude(game.getLongitude())
                .timezone(timezone)
                .hourlyWeather(hourlyWeatherDataList)
                .build();
            
            log.info("Loaded cached hourly weather data for game: {}", gameId);
            return ResponseEntity.ok(weatherResponse);
            
        } catch (Exception e) {
            log.error("Error loading cached weather for game: {}", gameId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Manually trigger data refresh
     */
    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refreshData() {
        log.info("POST /api/nfl/refresh - Manual data refresh triggered");
        try {
            nflWeatherService.refreshGameData();
            Map<String, String> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Game data refreshed successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error refreshing data", e);
            Map<String, String> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "NFL Weather Backend");
        return ResponseEntity.ok(response);
    }
}
