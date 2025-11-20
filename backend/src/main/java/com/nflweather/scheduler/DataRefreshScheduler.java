package com.nflweather.scheduler;

import com.nflweather.service.NFLWeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataRefreshScheduler {

    private final NFLWeatherService nflWeatherService;

    /**
     * Scheduled task to refresh NFL game data daily
     * Configured via application.properties: nfl.weather.refresh.cron
     */
    @Scheduled(cron = "${nfl.weather.refresh.cron}")
    public void scheduledRefresh() {
        log.info("Starting scheduled NFL game data refresh...");
        try {
            nflWeatherService.refreshGameData();
            log.info("Scheduled refresh completed successfully");
        } catch (Exception e) {
            log.error("Scheduled refresh failed", e);
        }
    }
}
