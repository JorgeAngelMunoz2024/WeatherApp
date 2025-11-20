package com.nflweather.service;

import com.nflweather.dto.GameWeatherResponse;
import com.nflweather.dto.HourlyWeatherData;
import com.nflweather.dto.OpenMeteoResponse;
import com.nflweather.model.NFLGame;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for fetching weather data from Open-Meteo API
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherAPIService {

    private static final String OPEN_METEO_URL = "https://api.open-meteo.com/v1/forecast";
    private static final int GAME_DURATION_HOURS = 3;
    // Open-Meteo API returns timestamps without seconds, e.g., "2025-11-23T13:00"
    private static final DateTimeFormatter OPEN_METEO_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    
    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Fetch hourly weather data for a game's duration (3 hours)
     * @param game The NFL game
     * @return GameWeatherResponse containing hourly weather data
     */
    public GameWeatherResponse getGameWeather(NFLGame game) {
        if (game == null || game.getLatitude() == null || game.getLongitude() == null) {
            log.error("Invalid game or missing coordinates");
            return null;
        }

        if (game.getGameday() == null || game.getGametime() == null) {
            log.error("Missing game date or time for game: {}", game.getGameId());
            return null;
        }

        try {
            // Combine game date and time
            LocalDateTime gameDateTime = LocalDateTime.of(game.getGameday(), game.getGametime());
            
            // Get timezone for the location (use system default for now, could be enhanced)
            ZoneId zoneId = ZoneId.systemDefault();
            ZonedDateTime gameStart = gameDateTime.atZone(zoneId);
            ZonedDateTime gameEnd = gameStart.plusHours(GAME_DURATION_HOURS);
            
            // Format for API (ISO 8601)
            String startTime = gameStart.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            String endTime = gameEnd.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            
            log.info("Fetching weather for game {} from {} to {}", 
                game.getGameId(), startTime, endTime);
            
            // Build API URL
            String url = UriComponentsBuilder.fromHttpUrl(OPEN_METEO_URL)
                .queryParam("latitude", game.getLatitude())
                .queryParam("longitude", game.getLongitude())
                .queryParam("hourly", "temperature_2m,precipitation_probability,precipitation,weather_code,wind_speed_10m")
                .queryParam("temperature_unit", "fahrenheit")
                .queryParam("wind_speed_unit", "mph")
                .queryParam("precipitation_unit", "inch")
                .queryParam("start_date", gameStart.toLocalDate().toString())
                .queryParam("end_date", gameEnd.toLocalDate().toString())
                .queryParam("timezone", "auto")
                .build()
                .toUriString();
            
            log.debug("Open-Meteo API URL: {}", url);
            
            // Call API
            OpenMeteoResponse response = restTemplate.getForObject(url, OpenMeteoResponse.class);
            
            if (response == null || response.getHourly() == null) {
                log.error("No weather data returned from API");
                return null;
            }
            
            // Extract hourly data for game duration
            List<HourlyWeatherData> hourlyWeatherList = extractGameHourlyData(
                response, 
                gameStart, 
                gameEnd
            );
            
            // Build response
            return GameWeatherResponse.builder()
                .gameId(game.getGameId())
                .homeTeam(game.getHomeTeam())
                .awayTeam(game.getAwayTeam())
                .stadium(game.getStadium())
                .location(game.getLocation())
                .gameTime(game.getGametime() != null ? game.getGametime().toString() : null)
                .gameDate(game.getGameday() != null ? game.getGameday().toString() : null)
                .latitude(response.getLatitude())
                .longitude(response.getLongitude())
                .timezone(response.getTimezone())
                .hourlyWeather(hourlyWeatherList)
                .build();
                
        } catch (Exception e) {
            log.error("Error fetching weather data for game {}", game.getGameId(), e);
            return null;
        }
    }

    /**
     * Extract hourly data for the game duration from the API response
     */
    private List<HourlyWeatherData> extractGameHourlyData(
            OpenMeteoResponse response, 
            ZonedDateTime gameStart, 
            ZonedDateTime gameEnd) {
        
        List<HourlyWeatherData> hourlyData = new ArrayList<>();
        OpenMeteoResponse.Hourly hourly = response.getHourly();
        
        if (hourly == null || hourly.getTime() == null) {
            return hourlyData;
        }
        
        List<String> times = hourly.getTime();
        List<Double> temperatures = hourly.getTemperature2m();
        List<Integer> precipProbs = hourly.getPrecipitationProbability();
        List<Double> precipitation = hourly.getPrecipitation();
        List<Integer> weatherCodes = hourly.getWeatherCode();
        List<Double> windSpeeds = hourly.getWindSpeed10m();
        
        // Parse each hour and check if it's within game duration
        for (int i = 0; i < times.size(); i++) {
            try {
                String timeStr = times.get(i);
                // Open-Meteo returns time in format "2025-11-23T13:00" without seconds
                // Parse using the custom formatter
                LocalDateTime localTime = LocalDateTime.parse(timeStr, OPEN_METEO_TIME_FORMAT);
                ZonedDateTime hourTime = localTime.atZone(gameStart.getZone());
                
                // Check if this hour is within game duration
                if (!hourTime.isBefore(gameStart) && hourTime.isBefore(gameEnd)) {
                    Integer weatherCode = weatherCodes != null && i < weatherCodes.size() ? weatherCodes.get(i) : null;
                    
                    HourlyWeatherData data = HourlyWeatherData.builder()
                        .time(timeStr)
                        .temperature(temperatures != null && i < temperatures.size() ? temperatures.get(i) : null)
                        .precipitationProbability(precipProbs != null && i < precipProbs.size() ? precipProbs.get(i) : null)
                        .precipitation(precipitation != null && i < precipitation.size() ? precipitation.get(i) : null)
                        .weatherCode(weatherCode)
                        .windSpeed(windSpeeds != null && i < windSpeeds.size() ? windSpeeds.get(i) : null)
                        .build();
                    
                    // Set weather description based on code
                    data.setWeatherDescription(data.getWeatherDescription());
                    
                    hourlyData.add(data);
                }
            } catch (Exception e) {
                log.warn("Error parsing hour data at index {}: {}", i, e.getMessage());
            }
        }
        
        log.info("Extracted {} hours of weather data for game duration", hourlyData.size());
        return hourlyData;
    }
}
