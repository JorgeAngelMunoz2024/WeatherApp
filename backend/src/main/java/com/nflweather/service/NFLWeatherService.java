package com.nflweather.service;

import com.nflweather.dto.GameWeatherResponse;
import com.nflweather.dto.HourlyWeatherData;
import com.nflweather.dto.OddsAPIResponse;
import com.nflweather.model.HourlyWeather;
import com.nflweather.model.NFLGame;
import com.nflweather.model.WeatherData;
import com.nflweather.repository.HourlyWeatherRepository;
import com.nflweather.repository.NFLGameRepository;
import com.nflweather.repository.WeatherDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NFLWeatherService {

    private final NFLGameRepository nflGameRepository;
    private final WeatherDataRepository weatherDataRepository;
    private final HourlyWeatherRepository hourlyWeatherRepository;
    private final OddsAPIService oddsAPIService;
    private final StadiumMappingService stadiumMappingService;
    private final WeatherAPIService weatherAPIService;

    /**
     * Refresh NFL and CFB game data from Odds API and fetch weather data
     */
    @Transactional
    public void refreshGameData() {
        try {
            log.info("Starting NFL and CFB game data refresh from Odds API...");
            
            // Fetch NFL games from Odds API
            List<OddsAPIResponse> nflGames = oddsAPIService.fetchNFLGames();
            log.info("Fetched {} NFL games", nflGames.size());
            
            // Fetch CFB games from Odds API
            List<OddsAPIResponse> cfbGames = oddsAPIService.fetchCFBGames();
            log.info("Fetched {} CFB games", cfbGames.size());
            
            // Combine both lists
            List<OddsAPIResponse> allGames = new ArrayList<>();
            allGames.addAll(nflGames);
            allGames.addAll(cfbGames);
            
            if (allGames.isEmpty()) {
                log.warn("No games fetched from Odds API");
                return;
            }
            
            // Convert to NFLGame entities (works for both NFL and CFB)
            List<NFLGame> games = allGames.stream()
                    .map(this::convertOddsAPIResponseToNFLGame)
                    .filter(game -> game != null)
                    .collect(Collectors.toList());
            
            // Clear old data and save new
            nflGameRepository.deleteAll();
            
            if (!games.isEmpty()) {
                nflGameRepository.saveAll(games);
                log.info("Successfully saved {} total games ({} NFL + {} CFB) in database", 
                        games.size(), nflGames.size(), cfbGames.size());
                
                // Fetch and cache weather data for each game
                fetchAndCacheWeatherForGames(games);
            } else {
                log.warn("No valid games to save after conversion");
            }
            
        } catch (Exception e) {
            log.error("Error refreshing game data", e);
            throw new RuntimeException("Failed to refresh game data: " + e.getMessage(), e);
        }
    }
    
    /**
     * Fetch weather data from API and cache in database
     */
    @Transactional
    public void fetchAndCacheWeatherForGames(List<NFLGame> games) {
        log.info("Fetching weather data for {} games...", games.size());
        
        int successCount = 0;
        for (NFLGame game : games) {
            try {
                // Fetch weather from API
                GameWeatherResponse weatherResponse = weatherAPIService.getGameWeather(game);
                
                if (weatherResponse != null && weatherResponse.getHourlyWeather() != null 
                    && !weatherResponse.getHourlyWeather().isEmpty()) {
                    
                    // Calculate average weather values
                    List<HourlyWeatherData> hourlyData = weatherResponse.getHourlyWeather();
                    double avgTemp = hourlyData.stream()
                        .mapToDouble(h -> h.getTemperature() != null ? h.getTemperature() : 0.0)
                        .average()
                        .orElse(0.0);
                    
                    double avgPrecipProb = hourlyData.stream()
                        .mapToDouble(h -> h.getPrecipitationProbability() != null ? h.getPrecipitationProbability() : 0.0)
                        .average()
                        .orElse(0.0);
                    
                    double avgPrecip = hourlyData.stream()
                        .mapToDouble(h -> h.getPrecipitation() != null ? h.getPrecipitation() : 0.0)
                        .average()
                        .orElse(0.0);
                    
                    double avgWind = hourlyData.stream()
                        .mapToDouble(h -> h.getWindSpeed() != null ? h.getWindSpeed() : 0.0)
                        .average()
                        .orElse(0.0);
                    
                    // Use first hour's weather code and description as representative
                    Integer weatherCode = hourlyData.get(0).getWeatherCode();
                    String weatherDesc = hourlyData.get(0).getWeatherDescription();
                    
                    // Save average weather data
                    WeatherData weatherData = WeatherData.builder()
                        .id(System.currentTimeMillis() + game.getGameId().hashCode()) // Generate unique ID
                        .game(game)
                        .avgTemperature(avgTemp)
                        .avgPrecipitationProbability((int) avgPrecipProb)
                        .avgPrecipitation(avgPrecip)
                        .avgWindSpeed(avgWind)
                        .weatherCode(weatherCode)
                        .weatherDescription(weatherDesc)
                        .timezone(weatherResponse.getTimezone())
                        .build();
                    
                    weatherDataRepository.save(weatherData);
                    
                    // Save hourly weather data
                    List<HourlyWeather> hourlyWeatherList = new ArrayList<>();
                    long baseId = System.currentTimeMillis();
                    for (int i = 0; i < hourlyData.size(); i++) {
                        HourlyWeatherData hourly = hourlyData.get(i);
                        HourlyWeather hw = HourlyWeather.builder()
                            .id(baseId + i + game.getGameId().hashCode()) // Generate unique ID
                            .game(game)
                            .time(hourly.getTime())
                            .temperature(hourly.getTemperature())
                            .precipitationProbability(hourly.getPrecipitationProbability())
                            .precipitation(hourly.getPrecipitation())
                            .weatherCode(hourly.getWeatherCode())
                            .weatherDescription(hourly.getWeatherDescription())
                            .windSpeed(hourly.getWindSpeed())
                            .build();
                        hourlyWeatherList.add(hw);
                    }
                    
                    hourlyWeatherRepository.saveAll(hourlyWeatherList);
                    successCount++;
                    
                    log.debug("Cached weather data for game: {}", game.getGameId());
                }
            } catch (Exception e) {
                log.error("Failed to fetch/cache weather for game {}: {}", game.getGameId(), e.getMessage());
            }
        }
        
        log.info("Successfully cached weather data for {}/{} games", successCount, games.size());
    }

    /**
     * Convert Odds API response to NFLGame entity (works for both NFL and CFB)
     */
    private NFLGame convertOddsAPIResponseToNFLGame(OddsAPIResponse oddsGame) {
        try {
            // Parse commence time
            Instant commenceInstant = Instant.parse(oddsGame.getCommenceTime());
            LocalDate gameday = commenceInstant.atZone(ZoneId.of("America/New_York")).toLocalDate();
            LocalTime gametime = commenceInstant.atZone(ZoneId.of("America/New_York")).toLocalTime();
            
            // Try NFL stadium first, then CFB
            StadiumMappingService.StadiumInfo stadiumInfo = stadiumMappingService.getNFLStadiumInfo(oddsGame.getHomeTeam());
            
            if (stadiumInfo == null) {
                // Try CFB stadium
                stadiumInfo = stadiumMappingService.getCFBStadiumInfo(oddsGame.getHomeTeam());
            }
            
            if (stadiumInfo == null) {
                log.warn("No stadium info for team: {}, skipping game", oddsGame.getHomeTeam());
                return null;
            }
            
            // Generate game ID
            String gameId = UUID.randomUUID().toString();
            
            // Determine season and week (simplified - you may want more sophisticated logic)
            int currentYear = LocalDate.now().getYear();
            int season = gameday.getMonthValue() < 3 ? currentYear - 1 : currentYear;
            int week = calculateWeek(gameday, season);
            
            // Determine league based on sport key
            String league = "americanfootball_ncaaf".equals(oddsGame.getSportKey()) ? "CFB" : "NFL";
            
            return NFLGame.builder()
                    .gameId(gameId)
                    .season(season)
                    .week(week)
                    .gameType("REG")
                    .league(league)
                    .awayTeam(oddsGame.getAwayTeam())
                    .homeTeam(oddsGame.getHomeTeam())
                    .gameday(gameday)
                    .gametime(gametime)
                    .stadium(stadiumInfo.getStadium())
                    .location(stadiumInfo.getCity() + ", " + stadiumInfo.getState())
                    .latitude(stadiumInfo.getLatitude())
                    .longitude(stadiumInfo.getLongitude())
                    .build();
                    
        } catch (Exception e) {
            log.error("Error converting Odds API game to NFLGame", e);
            return null;
        }
    }

    /**
     * Calculate NFL week based on date
     */
    private int calculateWeek(LocalDate gameday, int season) {
        // NFL season typically starts first Thursday after Labor Day (first Monday of September)
        LocalDate laborDay = LocalDate.of(season, 9, 1);
        while (laborDay.getDayOfWeek().getValue() != 1) { // Find first Monday
            laborDay = laborDay.plusDays(1);
        }
        LocalDate seasonStart = laborDay.plusDays(3); // First Thursday
        
        long daysDiff = java.time.temporal.ChronoUnit.DAYS.between(seasonStart, gameday);
        int week = (int) (daysDiff / 7) + 1;
        
        return Math.max(1, Math.min(week, 18)); // Clamp between 1 and 18
    }

    /**
     * Get all games
     */
    public List<NFLGame> getAllGames() {
        return nflGameRepository.findAll();
    }

    /**
     * Get games by league (NFL or CFB)
     */
    public List<NFLGame> getGamesByLeague(String league) {
        return nflGameRepository.findByLeague(league);
    }

    /**
     * Get games by week
     */
    public List<NFLGame> getGamesByWeek(Integer week) {
        return nflGameRepository.findByWeek(week);
    }

    /**
     * Get games by season and week
     */
    public List<NFLGame> getGamesBySeasonAndWeek(Integer season, Integer week) {
        return nflGameRepository.findBySeasonAndWeek(season, week);
    }

    /**
     * Get game by ID
     */
    public NFLGame getGameById(String gameId) {
        if (gameId == null) {
            return null;
        }
        return nflGameRepository.findById(gameId).orElse(null);
    }

    /**
     * Get games for a specific team
     */
    public List<NFLGame> getGamesByTeam(String team) {
        return nflGameRepository.findByHomeTeamOrAwayTeam(team, team);
    }
}
