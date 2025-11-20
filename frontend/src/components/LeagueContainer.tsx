import React from 'react';
import { GameWithAverageWeather } from '../types';
import GameCard from './GameCard';

interface LeagueContainerProps {
  league: 'NFL' | 'CFB';
  games: GameWithAverageWeather[];
  loading: boolean;
  onRefresh: () => void;
  searchQuery: string;
  highlightData?: {
    homeTeam: string;
    awayTeam: string;
    location: string;
  };
}

const LeagueContainer: React.FC<LeagueContainerProps> = ({ 
  league, 
  games, 
  loading, 
  onRefresh,
  searchQuery,
  highlightData
}) => {
  const icon = league === 'NFL' ? '🏈' : '🏛️';
  
  // Get current week's Thursday through Wednesday games
  const getCurrentWeekGames = (allGames: GameWithAverageWeather[]) => {
    const now = new Date();
    const currentDay = now.getDay(); // 0=Sunday, 1=Monday, ..., 6=Saturday
    
    // Calculate the most recent Thursday (start of NFL/CFB week)
    // Thursday is day 4
    let daysToSubtract;
    if (currentDay >= 4) {
      // If today is Thursday (4) or later, use this week's Thursday
      daysToSubtract = currentDay - 4;
    } else {
      // If today is before Thursday (Sun-Wed), use last week's Thursday
      daysToSubtract = currentDay + 3;
    }
    
    const weekStartThursday = new Date(now);
    weekStartThursday.setDate(now.getDate() - daysToSubtract);
    weekStartThursday.setHours(0, 0, 0, 0);
    
    // Calculate next Wednesday (end of current week)
    const weekEndWednesday = new Date(weekStartThursday);
    weekEndWednesday.setDate(weekStartThursday.getDate() + 6); // Thursday + 6 = Wednesday
    weekEndWednesday.setHours(23, 59, 59, 999);
    
    return allGames.filter(game => {
      const gameDate = new Date(`${game.gameday}T${game.gametime}`);
      return gameDate >= now && gameDate >= weekStartThursday && gameDate <= weekEndWednesday;
    });
  };
  
  const currentWeekGames = getCurrentWeekGames(games);
  
  const filteredGames = searchQuery 
    ? currentWeekGames.filter(game => {
        const query = searchQuery.toLowerCase();
        return game.homeTeam.toLowerCase().includes(query) ||
               game.awayTeam.toLowerCase().includes(query);
      })
    : currentWeekGames;

  const gamesToShow = filteredGames
    .sort((a, b) => {
      const dateA = new Date(`${a.gameday}T${a.gametime}`).getTime();
      const dateB = new Date(`${b.gameday}T${b.gametime}`).getTime();
      return dateA - dateB;
    });

  return (
    <div className="league-container">
      <div className="league-header">
        <span className="league-icon">{icon}</span>
        <h1 className="league-title">{league}</h1>
        <span 
          className="refresh-btn" 
          onClick={onRefresh} 
          title="Refresh"
        >
          🔄
        </span>
      </div>
      <div className="games-grid">
        {loading && <div className="loading">Loading {league} games...</div>}
        {!loading && gamesToShow.length === 0 && (
          <div className="loading">
            {searchQuery ? 'No games match your search' : 'No upcoming games available'}
          </div>
        )}
        {!loading && gamesToShow.map((game) => (
          <GameCard 
            key={game.gameId} 
            game={game}
            highlighted={searchQuery.length > 0}
            highlightData={highlightData}
          />
        ))}
      </div>
    </div>
  );
};

export default LeagueContainer;
