import React, { useState, useEffect } from 'react';
import SearchBar from './components/SearchBar';
import LeagueContainer from './components/LeagueContainer';
import { nflWeatherAPI } from './services/api';
import { GameWithAverageWeather } from './types';
import './App.css';

const App: React.FC = () => {
  const [nflGames, setNflGames] = useState<GameWithAverageWeather[]>([]);
  const [cfbGames, setCfbGames] = useState<GameWithAverageWeather[]>([]);
  const [nflLoading, setNflLoading] = useState(true);
  const [cfbLoading, setCfbLoading] = useState(true);
  const [searchQuery, setSearchQuery] = useState('');
  const [highlightData, setHighlightData] = useState<{
    homeTeam: string;
    awayTeam: string;
    location: string;
  } | undefined>(undefined);

  const fetchNFLGames = async () => {
    setNflLoading(true);
    try {
      const games = await nflWeatherAPI.getGamesByLeague('NFL');
      setNflGames(games);
    } catch (error) {
      console.error('Error fetching NFL games:', error);
    } finally {
      setNflLoading(false);
    }
  };

  const fetchCFBGames = async () => {
    setCfbLoading(true);
    try {
      const games = await nflWeatherAPI.getGamesByLeague('CFB');
      setCfbGames(games);
    } catch (error) {
      console.error('Error fetching CFB games:', error);
    } finally {
      setCfbLoading(false);
    }
  };

  useEffect(() => {
    fetchNFLGames();
    fetchCFBGames();

    // Refresh games every 5 minutes
    const interval = setInterval(() => {
      fetchNFLGames();
      fetchCFBGames();
    }, 5 * 60 * 1000);

    return () => clearInterval(interval);
  }, []);

  const handleSearch = (query: string) => {
    setSearchQuery(query);
    if (!query) {
      setHighlightData(undefined);
    }
  };

  const handleHighlight = (homeTeam: string, awayTeam: string, location: string) => {
    setHighlightData({ homeTeam, awayTeam, location });
  };

  return (
    <div className="app">
      <SearchBar 
        nflGames={nflGames} 
        cfbGames={cfbGames} 
        onSearch={handleSearch}
        onHighlight={handleHighlight}
      />
      
      <div className="calendar-section">
        <LeagueContainer
          league="CFB"
          games={cfbGames}
          loading={cfbLoading}
          onRefresh={fetchCFBGames}
          searchQuery={searchQuery}
          highlightData={highlightData}
        />

        <LeagueContainer
          league="NFL"
          games={nflGames}
          loading={nflLoading}
          onRefresh={fetchNFLGames}
          searchQuery={searchQuery}
          highlightData={highlightData}
        />
      </div>
    </div>
  );
};

export default App;
