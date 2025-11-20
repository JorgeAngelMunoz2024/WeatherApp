package com.nflweather.config;

import com.nflweather.repository.NFLGameRepository;
import com.nflweather.service.NFLWeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Load initial data on application startup if database is empty
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class StartupDataLoader implements CommandLineRunner {

    private final NFLGameRepository nflGameRepository;
    private final NFLWeatherService nflWeatherService;

    @Override
    public void run(String... args) {
        try {
            long gameCount = nflGameRepository.count();
            
            if (gameCount == 0) {
                log.info("Database is empty. Fetching initial NFL and CFB game data...");
                nflWeatherService.refreshGameData();
                log.info("Initial data load completed successfully!");
            } else {
                log.info("Database already contains {} games. Skipping initial data load.", gameCount);
            }
        } catch (Exception e) {
            log.error("Error during initial data load. You can manually refresh via /api/nfl/refresh endpoint.", e);
        }
    }
}
