-- NFL Weather Database Schema
-- SQLite Database Schema for NFL Games and Weather Data

-- Drop tables if they exist (for clean initialization)
DROP TABLE IF EXISTS hourly_weather;
DROP TABLE IF EXISTS weather_data;
DROP TABLE IF EXISTS nfl_games;
DROP TABLE IF EXISTS weather_data_seq;
DROP TABLE IF EXISTS hourly_weather_seq;

-- NFL Games Table
CREATE TABLE IF NOT EXISTS nfl_games (
    game_id VARCHAR(255) PRIMARY KEY,
    season INTEGER,
    week INTEGER,
    game_type VARCHAR(255),
    away_team VARCHAR(255),
    home_team VARCHAR(255),
    gameday DATE,
    gametime TIME,
    stadium VARCHAR(255),
    location VARCHAR(255),
    latitude REAL,
    longitude REAL,
    created_at VARCHAR(255),
    updated_at VARCHAR(255)
);

-- Weather Data Table (Average weather for the game)
CREATE TABLE IF NOT EXISTS weather_data (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    game_id VARCHAR(255) UNIQUE,
    avg_temperature REAL,
    avg_precipitation_probability INTEGER,
    avg_precipitation REAL,
    avg_wind_speed REAL,
    weather_code INTEGER,
    weather_description VARCHAR(255),
    timezone VARCHAR(255),
    created_at VARCHAR(255),
    updated_at VARCHAR(255),
    FOREIGN KEY (game_id) REFERENCES nfl_games(game_id) ON DELETE CASCADE
);

-- Hourly Weather Data Table (Hourly weather for each game - 3 hours)
CREATE TABLE IF NOT EXISTS hourly_weather (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    game_id VARCHAR(255),
    time VARCHAR(255),
    temperature REAL,
    precipitation_probability INTEGER,
    precipitation REAL,
    weather_code INTEGER,
    weather_description VARCHAR(255),
    wind_speed REAL,
    created_at VARCHAR(255),
    updated_at VARCHAR(255),
    FOREIGN KEY (game_id) REFERENCES nfl_games(game_id) ON DELETE CASCADE
);

-- Indexes for better query performance
CREATE INDEX IF NOT EXISTS idx_nfl_games_season ON nfl_games(season);
CREATE INDEX IF NOT EXISTS idx_nfl_games_week ON nfl_games(week);
CREATE INDEX IF NOT EXISTS idx_nfl_games_season_week ON nfl_games(season, week);
CREATE INDEX IF NOT EXISTS idx_nfl_games_home_team ON nfl_games(home_team);
CREATE INDEX IF NOT EXISTS idx_nfl_games_away_team ON nfl_games(away_team);
CREATE INDEX IF NOT EXISTS idx_nfl_games_gameday ON nfl_games(gameday);
CREATE INDEX IF NOT EXISTS idx_weather_data_game_id ON weather_data(game_id);
CREATE INDEX IF NOT EXISTS idx_hourly_weather_game_id ON hourly_weather(game_id);
