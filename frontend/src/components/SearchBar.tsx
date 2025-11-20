import React, { useState, useEffect, useRef } from 'react';
import { GameWithAverageWeather, SearchSuggestion } from '../types';

interface SearchBarProps {
  nflGames: GameWithAverageWeather[];
  cfbGames: GameWithAverageWeather[];
  onSearch: (query: string) => void;
  onHighlight: (homeTeam: string, awayTeam: string, location: string) => void;
}

const SearchBar: React.FC<SearchBarProps> = ({ nflGames, cfbGames, onSearch, onHighlight }) => {
  const [searchQuery, setSearchQuery] = useState('');
  const [suggestions, setSuggestions] = useState<SearchSuggestion[]>([]);
  const [showSuggestions, setShowSuggestions] = useState(false);
  const searchRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      if (searchRef.current && !searchRef.current.contains(event.target as Node)) {
        setShowSuggestions(false);
      }
    };

    document.addEventListener('mousedown', handleClickOutside);
    return () => document.removeEventListener('mousedown', handleClickOutside);
  }, []);

  useEffect(() => {
    if (!searchQuery.trim()) {
      setSuggestions([]);
      setShowSuggestions(false);
      onSearch('');
      return;
    }

    const query = searchQuery.toLowerCase();
    const allGames = [...nflGames, ...cfbGames];
    const results: SearchSuggestion[] = [];

    allGames.forEach(game => {
      const homeTeam = game.homeTeam.toLowerCase();
      const awayTeam = game.awayTeam.toLowerCase();
      const location = game.location.toLowerCase();
      const stadium = game.stadium?.toLowerCase() || '';

      // Check if any field matches the query
      const homeTeamMatches = homeTeam.includes(query);
      const awayTeamMatches = awayTeam.includes(query);
      const locationMatches = location.includes(query);
      const stadiumMatches = stadium.includes(query);

      if (homeTeamMatches) {
        results.push({
          type: 'team',
          title: game.homeTeam,
          subtitle: `Home Game at ${game.location}`,
          homeTeam: game.homeTeam,
          awayTeam: game.awayTeam,
          location: game.location
        });
      }

      if (awayTeamMatches) {
        results.push({
          type: 'team',
          title: game.awayTeam,
          subtitle: `Away Game at ${game.location}`,
          homeTeam: game.homeTeam,
          awayTeam: game.awayTeam,
          location: game.location
        });
      }

      if (locationMatches || stadiumMatches) {
        // Only add location result if we haven't already added team results for this game
        if (!homeTeamMatches && !awayTeamMatches) {
          results.push({
            type: 'location',
            title: game.location,
            subtitle: `${game.awayTeam} @ ${game.homeTeam}`,
            homeTeam: game.homeTeam,
            awayTeam: game.awayTeam,
            location: game.location
          });
        }
      }
    });

    // Remove duplicates
    const unique = results.filter((item, index, self) =>
      index === self.findIndex(t => t.title === item.title && t.subtitle === item.subtitle)
    );

    setSuggestions(unique.slice(0, 10));
    setShowSuggestions(true);
  }, [searchQuery, nflGames, cfbGames, onSearch]);

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setSearchQuery(e.target.value);
    onSearch(e.target.value);
  };

  const handleSuggestionClick = (suggestion: SearchSuggestion) => {
    setSearchQuery(suggestion.title);
    setShowSuggestions(false);
    onSearch(suggestion.title);
    
    // Highlight the game card
    if (suggestion.homeTeam && suggestion.awayTeam && suggestion.location) {
      onHighlight(suggestion.homeTeam, suggestion.awayTeam, suggestion.location);
      
      // Scroll to the game card
      setTimeout(() => {
        const cards = document.querySelectorAll('.game-card');
        cards.forEach(card => {
          const cardElement = card as HTMLElement;
          const homeTeam = cardElement.dataset.home;
          const awayTeam = cardElement.dataset.away;
          
          if ((homeTeam === suggestion.homeTeam && awayTeam === suggestion.awayTeam) ||
              (homeTeam === suggestion.awayTeam && awayTeam === suggestion.homeTeam)) {
            cardElement.scrollIntoView({ behavior: 'smooth', block: 'center' });
          }
        });
      }, 100);
    }
  };

  return (
    <div className="search-container" ref={searchRef}>
      <form className="search-form" role="search" onSubmit={(e) => e.preventDefault()}>
        <div className="search-wrapper">
          <span className="search-icon">🔍</span>
          <input
            className="search-input"
            type="search"
            placeholder="Search for teams, cities, or stadiums..."
            id="stadium-search"
            autoComplete="off"
            value={searchQuery}
            onChange={handleInputChange}
            onFocus={() => searchQuery && setShowSuggestions(true)}
          />
        </div>
        {showSuggestions && suggestions.length > 0 && (
          <div className="search-suggestions">
            {suggestions.map((suggestion, index) => (
              <div
                key={index}
                className="suggestion-item"
                onClick={() => handleSuggestionClick(suggestion)}
              >
                <span className="suggestion-icon">
                  {suggestion.type === 'team' ? '🏈' : '📍'}
                </span>
                <div className="suggestion-content">
                  <div className="suggestion-title">{suggestion.title}</div>
                  <div className="suggestion-subtitle">{suggestion.subtitle}</div>
                </div>
              </div>
            ))}
          </div>
        )}
        {showSuggestions && searchQuery && suggestions.length === 0 && (
          <div className="search-suggestions">
            <div className="suggestion-item no-results">No results found</div>
          </div>
        )}
      </form>
    </div>
  );
};

export default SearchBar;
