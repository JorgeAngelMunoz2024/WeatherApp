package com.nflweather.repository;

import com.nflweather.model.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {
    
    Optional<WeatherData> findByGame_GameId(String gameId);
    
    void deleteByGame_GameId(String gameId);
}
