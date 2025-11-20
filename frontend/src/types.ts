// Types and Interfaces
export interface NFLGame {
  gameId: string;
  season: number;
  week: number;
  gameDate: string;
  gameTime: string;
  homeTeam: string;
  awayTeam: string;
  stadium: string;
  city: string;
  state: string;
  weatherCondition?: string;
  temperature?: number;
  windSpeed?: number;
  precipitation?: number;
  humidity?: number;
  lastUpdated?: string;
}

export interface GameWithAverageWeather {
  gameId: string;
  season: number;
  week: number;
  gameType: string;
  awayTeam: string;
  homeTeam: string;
  gameday: string;
  gametime: string;
  stadium: string;
  location: string;
  latitude: number;
  longitude: number;
  avgTemperature?: number;
  avgPrecipitationProbability?: number;
  avgWindSpeed?: number;
  weatherDescription?: string;
  weatherCode?: number;
}

export interface HourlyWeatherData {
  time: string;
  temperature: number;
  precipitationProbability: number;
  precipitation: number;
  weatherCode: number;
  weatherDescription: string;
  windSpeed: number;
}

export interface GameWeatherResponse {
  gameId: string;
  homeTeam: string;
  awayTeam: string;
  stadium: string;
  location: string;
  gameTime: string;
  gameDate: string;
  latitude: number;
  longitude: number;
  timezone: string;
  hourlyWeather: HourlyWeatherData[];
}

export interface OddsAPIGame {
  id: string;
  sport_key: string;
  sport_title: string;
  commence_time: string;
  home_team: string;
  away_team: string;
  bookmakers?: any[];
}

export interface StadiumLocations {
  [teamName: string]: string;
}

export interface SearchSuggestion {
  type: 'team' | 'location';
  title: string;
  subtitle: string;
  homeTeam?: string;
  awayTeam?: string;
  location?: string;
}
