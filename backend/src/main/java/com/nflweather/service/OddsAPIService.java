package com.nflweather.service;

import com.nflweather.dto.OddsAPIResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * Service to fetch game data from The Odds API
 */
@Service
@Slf4j
public class OddsAPIService {

    private final RestTemplate restTemplate;

    @Value("${odds.api.key}")
    private String apiKey;

    @Value("${odds.api.base.url:https://api.the-odds-api.com/v4}")
    private String baseUrl;

    public OddsAPIService() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * Fetch NFL games from Odds API
     */
    public List<OddsAPIResponse> fetchNFLGames() {
        try {
            log.info("Fetching NFL games from Odds API");
            String url = String.format("%s/sports/americanfootball_nfl/odds/?apiKey=%s&regions=us&markets=h2h",
                    baseUrl, apiKey);
            
            OddsAPIResponse[] response = restTemplate.getForObject(url, OddsAPIResponse[].class);
            
            if (response != null) {
                log.info("Successfully fetched {} NFL games", response.length);
                return Arrays.asList(response);
            }
            
            log.warn("No NFL games returned from API");
            return List.of();
        } catch (Exception e) {
            log.error("Error fetching NFL games from Odds API", e);
            return List.of();
        }
    }

    /**
     * Fetch CFB (FBS) games from Odds API
     */
    public List<OddsAPIResponse> fetchCFBGames() {
        try {
            log.info("Fetching CFB games from Odds API");
            String url = String.format("%s/sports/americanfootball_ncaaf/odds/?apiKey=%s&regions=us&markets=h2h",
                    baseUrl, apiKey);
            
            OddsAPIResponse[] response = restTemplate.getForObject(url, OddsAPIResponse[].class);
            
            if (response != null) {
                log.info("Successfully fetched {} CFB games", response.length);
                return Arrays.asList(response);
            }
            
            log.warn("No CFB games returned from API");
            return List.of();
        } catch (Exception e) {
            log.error("Error fetching CFB games from Odds API", e);
            return List.of();
        }
    }
}
