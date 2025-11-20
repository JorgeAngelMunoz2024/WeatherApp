package com.nflweather.repository;

import com.nflweather.model.HourlyWeather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HourlyWeatherRepository extends JpaRepository<HourlyWeather, Long> {
    
    List<HourlyWeather> findByGame_GameId(String gameId);
    
    void deleteByGame_GameId(String gameId);
}
