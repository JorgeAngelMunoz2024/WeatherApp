import axios from 'axios';
import { API_BASE_URL, ODDS_API_TOKEN } from '../config';
import { NFLGame, OddsAPIGame, GameWeatherResponse, GameWithAverageWeather } from '../types';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

export const nflWeatherAPI = {
  // Get all NFL games from our backend
  getAllGames: async (): Promise<GameWithAverageWeather[]> => {
    const response = await api.get<GameWithAverageWeather[]>('/api/nfl/games');
    return response.data;
  },

  // Get games by league (NFL or CFB)
  getGamesByLeague: async (league: 'NFL' | 'CFB'): Promise<GameWithAverageWeather[]> => {
    const response = await api.get<GameWithAverageWeather[]>(`/api/nfl/games?league=${league}`);
    return response.data;
  },

  // Get game by ID
  getGameById: async (gameId: string): Promise<NFLGame> => {
    const response = await api.get<NFLGame>(`/api/nfl/games/${gameId}`);
    return response.data;
  },

  // Get detailed hourly weather for a game
  getGameWeather: async (gameId: string): Promise<GameWeatherResponse> => {
    const response = await api.get<GameWeatherResponse>(`/api/nfl/games/${gameId}/weather`);
    return response.data;
  },

  // Get games by week
  getGamesByWeek: async (week: number): Promise<NFLGame[]> => {
    const response = await api.get<NFLGame[]>(`/api/nfl/games/week/${week}`);
    return response.data;
  },

  // Get games by team
  getGamesByTeam: async (team: string): Promise<NFLGame[]> => {
    const response = await api.get<NFLGame[]>(`/api/nfl/games/team/${team}`);
    return response.data;
  },

  // Refresh data
  refreshData: async (): Promise<{ status: string; message: string }> => {
    const response = await api.post('/api/nfl/refresh');
    return response.data;
  },

  // Health check
  healthCheck: async (): Promise<{ status: string; service: string }> => {
    const response = await api.get('/api/nfl/health');
    return response.data;
  },
};

// Fetch from Odds API (CFB and NFL upcoming games)
export const oddsAPI = {
  getNFLGames: async (): Promise<OddsAPIGame[]> => {
    try {
      const response = await axios.get(
        `https://corsproxy.io/?https://api.the-odds-api.com/v4/sports/americanfootball_nfl/odds/?apiKey=${ODDS_API_TOKEN}&regions=us&markets=h2h`
      );
      return response.data;
    } catch (error) {
      console.error('Error fetching NFL games from Odds API:', error);
      return [];
    }
  },

  getCFBGames: async (): Promise<OddsAPIGame[]> => {
    try {
      const response = await axios.get(
        `https://corsproxy.io/?https://api.the-odds-api.com/v4/sports/americanfootball_ncaaf/odds/?apiKey=${ODDS_API_TOKEN}&regions=us&markets=h2h`
      );
      return response.data;
    } catch (error) {
      console.error('Error fetching CFB games from Odds API:', error);
      return [];
    }
  },
};

export default api;
