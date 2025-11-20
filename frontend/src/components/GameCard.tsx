import React, { useState } from 'react';
import { 
  Card, 
  CardContent, 
  IconButton, 
  Typography, 
  Box, 
  Dialog,
  DialogContent,
  DialogTitle
} from '@mui/material';
import { GameWithAverageWeather, GameWeatherResponse } from '../types';
import { getTeamInitials, truncateTeamName } from '../utils/teams';
import { nflWeatherAPI } from '../services/api';

interface GameCardProps {
  game: GameWithAverageWeather;
  highlighted?: boolean;
  highlightData?: {
    homeTeam: string;
    awayTeam: string;
    location: string;
  };
}

const GameCard: React.FC<GameCardProps> = ({ game, highlighted = false, highlightData }) => {
  const [expanded, setExpanded] = useState(false);
  const [weatherData, setWeatherData] = useState<GameWeatherResponse | null>(null);
  const [loading, setLoading] = useState(false);
  
  const gameTime = new Date(`${game.gameday}T${game.gametime}`);
  const now = new Date();
  const isToday = gameTime.toDateString() === now.toDateString();
  
  const timeString = gameTime.toLocaleTimeString('en-US', { 
    hour: 'numeric', 
    minute: '2-digit',
    hour12: true 
  });
  
  const dateString = isToday ? 'Today' : gameTime.toLocaleDateString('en-US', { 
    month: 'short', 
    day: 'numeric' 
  });

  const awayTeam = game.awayTeam;
  const homeTeam = game.homeTeam;
  const stadiumLocation = game.location;
  
  // Check if this card should be highlighted based on search
  const isHighlighted = highlighted || (
    highlightData && (
      (highlightData.homeTeam === homeTeam && highlightData.awayTeam === awayTeam) ||
      highlightData.location === stadiumLocation
    )
  );
  
  // Handle card click to fetch weather
  const handleCardClick = async () => {
    setExpanded(true);
    if (!weatherData) {
      setLoading(true);
      try {
        const gameId = game.gameId;
        const data = await nflWeatherAPI.getGameWeather(gameId);
        setWeatherData(data);
      } catch (error) {
        console.error('Error fetching weather:', error);
      } finally {
        setLoading(false);
      }
    }
  };
  
  const handleClose = () => {
    setExpanded(false);
  };
  
  // Format time from ISO string to readable format
  const formatTime = (isoTime: string) => {
    const date = new Date(isoTime);
    return date.toLocaleTimeString('en-US', { 
      hour: 'numeric', 
      minute: '2-digit',
      hour12: true 
    });
  };
  
  return (
    <>
      <Card 
        className={`game-card ${isHighlighted ? 'highlighted' : ''}`}
        onClick={handleCardClick}
        sx={{
          background: isHighlighted 
            ? 'rgba(135, 206, 235, 0.2)' 
            : 'rgba(255, 255, 255, 0.08)',
          backdropFilter: 'blur(10px)',
          borderRadius: '10px',
          cursor: 'pointer',
          transition: 'all 0.3s ease',
          border: isHighlighted ? '2px solid #87ceeb' : 'none',
          boxShadow: isHighlighted 
            ? '0 0 20px rgba(135, 206, 235, 0.3)' 
            : 'none',
          minHeight: '200px',
          display: 'flex',
          flexDirection: 'column',
          '&:hover': {
            background: 'rgba(255, 255, 255, 0.15)',
            transform: 'scale(1.05) translateY(-4px)',
            boxShadow: '0 8px 24px rgba(0, 0, 0, 0.4)',
          },
          '&:active': {
            transform: 'scale(0.98)',
          }
        }}
      >
        <CardContent sx={{ p: 1.5, '&:last-child': { pb: 1.5 }, flex: 1, display: 'flex', flexDirection: 'column', gap: 1 }}>
          <Box className="teams" sx={{ display: 'flex', flexDirection: 'column', gap: 1, flex: 1 }}>
            <Box className="team" sx={{ display: 'flex', alignItems: 'center', gap: 1 }}>
              <Box className="team-logo" sx={{ 
                width: 22, 
                height: 22, 
                borderRadius: '50%', 
                background: 'white', 
                display: 'flex', 
                alignItems: 'center', 
                justifyContent: 'center', 
                fontSize: 10, 
                fontWeight: 600 
              }}>
                {getTeamInitials(awayTeam)}
              </Box>
              <Typography variant="body2" sx={{ color: 'white', fontSize: 13, fontWeight: 500 }} noWrap>
                {truncateTeamName(awayTeam)}
              </Typography>
            </Box>
            <Box className="team" sx={{ display: 'flex', alignItems: 'center', gap: 1 }}>
              <Box className="team-logo" sx={{ 
                width: 22, 
                height: 22, 
                borderRadius: '50%', 
                background: 'white', 
                display: 'flex', 
                alignItems: 'center', 
                justifyContent: 'center', 
                fontSize: 10, 
                fontWeight: 600 
              }}>
                {getTeamInitials(homeTeam)}
              </Box>
              <Typography variant="body2" sx={{ color: 'white', fontSize: 13, fontWeight: 500 }} noWrap>
                {truncateTeamName(homeTeam)}
              </Typography>
            </Box>
          </Box>

          <Typography 
            variant="body2" 
            sx={{ 
              color: 'white', 
              fontSize: 13, 
              fontWeight: 600, 
              textAlign: 'center', 
              pt: 0.5, 
              borderTop: '1px solid rgba(255, 255, 255, 0.1)' 
            }}
          >
            {dateString} • {timeString}
          </Typography>

          <Typography 
            variant="caption" 
            sx={{ color: 'rgba(255, 255, 255, 0.7)', fontSize: 11, textAlign: 'center' }}
          >
            📍 {stadiumLocation}
          </Typography>

          {game.avgTemperature && (
            <Box sx={{ 
              display: 'flex', 
              flexDirection: 'column', 
              gap: 0.5, 
              mt: 1.5, 
              pt: 1.5, 
              borderTop: '1px solid rgba(255, 255, 255, 0.2)', 
              fontSize: 12 
            }}>
              <Typography sx={{ fontWeight: 600, color: '#87CEEB', fontSize: 12 }}>
                🌡️ {Math.round(game.avgTemperature)}°F
              </Typography>
              {game.weatherDescription && (
                <Typography sx={{ fontSize: 11, color: 'rgba(255, 255, 255, 0.8)' }}>
                  {game.weatherDescription}
                </Typography>
              )}
              {game.avgWindSpeed && (
                <Typography sx={{ fontSize: 11, color: 'rgba(255, 255, 255, 0.7)' }}>
                  💨 {Math.round(game.avgWindSpeed)} mph
                </Typography>
              )}
            </Box>
          )}
        </CardContent>
      </Card>

      <Dialog
        open={expanded}
        onClose={handleClose}
        maxWidth="md"
        fullWidth
        TransitionProps={{
          timeout: 400,
        }}
        PaperProps={{
          sx: {
            background: 'linear-gradient(135deg, rgba(30, 58, 95, 0.98) 0%, rgba(45, 74, 111, 0.98) 100%)',
            backdropFilter: 'blur(10px)',
            border: '2px solid rgba(135, 206, 235, 0.3)',
            borderRadius: '16px',
            color: 'white',
          }
        }}
        sx={{
          '& .MuiBackdrop-root': {
            backgroundColor: 'rgba(0, 0, 0, 0.7)',
          }
        }}
      >
        <DialogTitle sx={{ 
          display: 'flex', 
          justifyContent: 'space-between', 
          alignItems: 'center',
          pb: 1,
          borderBottom: '1px solid rgba(255, 255, 255, 0.1)'
        }}>
          <Box>
            <Typography variant="h6" sx={{ fontWeight: 600, mb: 0.5 }}>
              {awayTeam} @ {homeTeam}
            </Typography>
            <Typography variant="body2" sx={{ color: 'rgba(255, 255, 255, 0.7)' }}>
              {dateString} • {timeString} • {stadiumLocation}
            </Typography>
          </Box>
          <IconButton 
            onClick={handleClose}
            sx={{ 
              color: 'white',
              '&:hover': {
                background: 'rgba(255, 255, 255, 0.1)',
                transform: 'rotate(90deg)',
              },
              transition: 'all 0.2s'
            }}
          >
            ✕
          </IconButton>
        </DialogTitle>

        <DialogContent sx={{ pt: 2 }}>
          {loading ? (
            <Box sx={{ textAlign: 'center', py: 4, color: 'rgba(255, 255, 255, 0.7)' }}>
              Loading weather data...
            </Box>
          ) : weatherData ? (
            <Box>
              <Typography variant="h6" sx={{ mb: 1, fontWeight: 600 }}>
                Weather Forecast
              </Typography>
              <Typography variant="body2" sx={{ color: 'rgba(255, 255, 255, 0.7)', mb: 2 }}>
                {weatherData.location}
              </Typography>
              
              <Box sx={{ 
                display: 'flex', 
                gap: 1.5, 
                overflowX: 'auto',
                pb: 1,
                '&::-webkit-scrollbar': {
                  height: '6px',
                },
                '&::-webkit-scrollbar-track': {
                  background: 'rgba(255, 255, 255, 0.05)',
                  borderRadius: '3px',
                },
                '&::-webkit-scrollbar-thumb': {
                  background: 'rgba(255, 255, 255, 0.2)',
                  borderRadius: '3px',
                  '&:hover': {
                    background: 'rgba(255, 255, 255, 0.3)',
                  }
                }
              }}>
                {weatherData.hourlyWeather.map((hour, index) => (
                  <Card 
                    key={index}
                    sx={{ 
                      background: 'rgba(255, 255, 255, 0.1)',
                      minWidth: 120,
                      flexShrink: 0,
                    }}
                  >
                    <CardContent sx={{ textAlign: 'center', p: 1.5, '&:last-child': { pb: 1.5 } }}>
                      <Typography sx={{ color: 'rgba(255, 255, 255, 0.8)', fontSize: 13, fontWeight: 600, mb: 1 }}>
                        {formatTime(hour.time)}
                      </Typography>
                      <Typography sx={{ color: 'white', fontSize: 24, fontWeight: 700, mb: 0.5 }}>
                        {Math.round(hour.temperature)}°F
                      </Typography>
                      <Typography sx={{ color: 'rgba(255, 255, 255, 0.9)', fontSize: 12, mb: 1 }}>
                        {hour.weatherDescription}
                      </Typography>
                      <Box sx={{ display: 'flex', flexDirection: 'column', gap: 0.5, fontSize: 11, color: 'rgba(255, 255, 255, 0.7)' }}>
                        <Typography sx={{ fontSize: 11 }}>💨 {Math.round(hour.windSpeed)} mph</Typography>
                        <Typography sx={{ fontSize: 11 }}>💧 {hour.precipitationProbability}%</Typography>
                      </Box>
                    </CardContent>
                  </Card>
                ))}
              </Box>
            </Box>
          ) : null}
        </DialogContent>
      </Dialog>
    </>
  );
};

export default GameCard;
