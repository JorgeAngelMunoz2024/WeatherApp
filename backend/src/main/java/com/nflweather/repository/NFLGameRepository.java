package com.nflweather.repository;

import com.nflweather.model.NFLGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface NFLGameRepository extends JpaRepository<NFLGame, String> {
    
    List<NFLGame> findByWeek(Integer week);
    
    List<NFLGame> findBySeason(Integer season);
    
    List<NFLGame> findBySeasonAndWeek(Integer season, Integer week);
    
    List<NFLGame> findByGamedayBetween(LocalDate startDate, LocalDate endDate);
    
    List<NFLGame> findByHomeTeamOrAwayTeam(String homeTeam, String awayTeam);
    
    List<NFLGame> findByLeague(String league);
    
    List<NFLGame> findByLeagueAndWeek(String league, Integer week);
    
    List<NFLGame> findByLeagueAndSeasonAndWeek(String league, Integer season, Integer week);
}
