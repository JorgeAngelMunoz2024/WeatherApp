# NFL Weather Backend

A Spring Boot backend service that fetches current week's NFL game schedules and weather data for game locations. The service uses Python with `nfl-data-py` for NFL data and Open-Meteo API for weather forecasts, stores data in SQLite, and provides REST APIs for frontend consumption.

## Features

- 🏈 Fetches current week's NFL games using `nfl-data-py`
- 🌦️ Retrieves weather forecasts for game stadiums using Open-Meteo API
- 💾 Stores data in SQLite database
- 🔄 Daily scheduled refresh (6 AM default)
- 🐳 Dockerized with Maven build
- 🌐 CORS-enabled REST APIs for React frontend
- 📊 Comprehensive game and weather data endpoints

## Tech Stack

- **Backend**: Java 17, Spring Boot 3.2.0
- **Database**: SQLite with Hibernate
- **Data Fetching**: Python 3 with nfl-data-py 0.3.3
- **Weather API**: Open-Meteo
- **Build Tool**: Maven
- **Containerization**: Docker & Docker Compose

## Project Structure

```
nfl-weather-backend/
├── src/
│   └── main/
│       ├── java/com/nflweather/
│       │   ├── NflWeatherBackendApplication.java
│       │   ├── config/
│       │   │   └── JacksonConfig.java
│       │   ├── controller/
│       │   │   └── NFLWeatherController.java
│       │   ├── dto/
│       │   │   └── PythonGameData.java
│       │   ├── model/
│       │   │   ├── NFLGame.java
│       │   │   └── WeatherData.java
│       │   ├── repository/
│       │   │   ├── NFLGameRepository.java
│       │   │   └── WeatherDataRepository.java
│       │   ├── scheduler/
│       │   │   └── DataRefreshScheduler.java
│       │   └── service/
│       │       └── NFLWeatherService.java
│       └── resources/
│           └── application.properties
├── scripts/
│   ├── fetch_nfl_weather.py
│   └── requirements.txt
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── README.md
```

## Prerequisites

### For Docker Deployment (Recommended)
- Docker
- Docker Compose

### For Local Development
- Java 17 or higher
- Maven 3.6+
- Python 3.8+
- pip

## Quick Start with Docker

1. **Clone or navigate to the project directory**:
   ```bash
   cd nfl-weather-backend
   ```

2. **Build and run with Docker Compose**:
   ```bash
   # Windows
   build.bat
   docker-compose up

   # Linux/Mac
   chmod +x build.sh
   ./build.sh
   docker-compose up
   ```

3. **Access the API**:
   - Base URL: `http://localhost:8080`
   - Health check: `http://localhost:8080/api/nfl/health`

## Local Development Setup

1. **Install Python dependencies**:
   ```bash
   cd scripts
   pip install -r requirements.txt
   cd ..
   ```

2. **Build with Maven**:
   ```bash
   mvn clean package
   ```

3. **Run the application**:
   ```bash
   java -jar target/nfl-weather-backend-1.0.0.jar
   ```

   Or use Maven:
   ```bash
   mvn spring-boot:run
   ```

## API Endpoints

### Game Data

- **GET** `/api/nfl/games` - Get all games
  ```bash
  curl http://localhost:8080/api/nfl/games
  ```

- **GET** `/api/nfl/games/{gameId}` - Get specific game
  ```bash
  curl http://localhost:8080/api/nfl/games/2025_01_BUF_KC
  ```

- **GET** `/api/nfl/games/week/{week}` - Get games by week
  ```bash
  curl http://localhost:8080/api/nfl/games/week/12
  ```

- **GET** `/api/nfl/games/season/{season}/week/{week}` - Get games by season and week
  ```bash
  curl http://localhost:8080/api/nfl/games/season/2025/week/12
  ```

- **GET** `/api/nfl/games/team/{team}` - Get games for specific team
  ```bash
  curl http://localhost:8080/api/nfl/games/team/KC
  ```

### Data Management

- **POST** `/api/nfl/refresh` - Manually trigger data refresh
  ```bash
  curl -X POST http://localhost:8080/api/nfl/refresh
  ```

- **GET** `/api/nfl/health` - Health check endpoint
  ```bash
  curl http://localhost:8080/api/nfl/health
  ```

## Example Response

```json
{
  "gameId": "2025_12_BUF_KC",
  "season": 2025,
  "week": 12,
  "gameType": "REG",
  "awayTeam": "BUF",
  "homeTeam": "KC",
  "gameday": "2025-11-24",
  "gametime": "16:25:00",
  "stadium": "GEHA Field at Arrowhead Stadium",
  "location": "Kansas City",
  "latitude": 39.0489,
  "longitude": -94.4839,
  "weather": {
    "id": 1,
    "temperature": 45.2,
    "precipitationProbability": 20,
    "precipitation": 0.0,
    "weatherCode": 2,
    "windSpeed": 12.5,
    "forecastTime": "2025-11-24T16:00:00Z",
    "weatherDescription": "Partly cloudy"
  },
  "createdAt": "2025-11-18T10:30:00",
  "updatedAt": "2025-11-18T10:30:00"
}
```

## Configuration

Edit `src/main/resources/application.properties`:

```properties
# Server Configuration
server.port=8080

# Database Configuration
spring.datasource.url=jdbc:sqlite:nflweather.db

# Python Script Configuration
python.script.path=scripts/fetch_nfl_weather.py
python.executable=python3

# Scheduler Configuration (Cron format: sec min hour day month weekday)
nfl.weather.refresh.cron=0 0 6 * * ?  # Daily at 6 AM

# CORS Configuration
cors.allowed.origins=http://localhost:3000,http://localhost:5173
```

## Testing the Backend

### 1. Test Python Script Directly
```bash
cd scripts
python fetch_nfl_weather.py
```

### 2. Test Data Refresh
```bash
curl -X POST http://localhost:8080/api/nfl/refresh
```

### 3. Test Retrieving Games
```bash
# Get all games
curl http://localhost:8080/api/nfl/games | python -m json.tool

# Get current week games (adjust week number)
curl http://localhost:8080/api/nfl/games/week/12 | python -m json.tool
```

### 4. Test with PowerShell (Windows)
```powershell
# Trigger refresh
Invoke-RestMethod -Uri http://localhost:8080/api/nfl/refresh -Method Post

# Get games
Invoke-RestMethod -Uri http://localhost:8080/api/nfl/games | ConvertTo-Json -Depth 10
```

## Scheduled Refresh

The application automatically refreshes NFL game data daily at 6 AM (configurable via `nfl.weather.refresh.cron`). You can also manually trigger a refresh using the `/api/nfl/refresh` endpoint.

## Weather Codes

The weather API returns numeric codes that are converted to descriptions:

- `0`: Clear sky
- `1-3`: Partly cloudy
- `45, 48`: Foggy
- `51, 53, 55`: Drizzle
- `61, 63, 65`: Rain
- `71, 73, 75`: Snow
- `80-82`: Rain showers
- `85-86`: Snow showers
- `95`: Thunderstorm
- `96, 99`: Thunderstorm with hail

## Database

SQLite database (`nflweather.db`) stores:

- **nfl_games**: Game information (teams, stadium, location, coordinates)
- **weather_data**: Weather forecasts (temperature, precipitation, wind speed)

Database is automatically created and updated via JPA/Hibernate.

## Docker Volumes

The docker-compose configuration mounts:
- `./data:/app/data` - Data directory
- `./nflweather.db:/app/nflweather.db` - SQLite database

This ensures data persists between container restarts.

## Connecting to React Frontend

The backend is CORS-enabled for React frontends running on:
- `http://localhost:3000` (Create React App default)
- `http://localhost:5173` (Vite default)

To add more origins, update `cors.allowed.origins` in `application.properties`.

### React Fetch Example
```javascript
// Fetch all games
const response = await fetch('http://localhost:8080/api/nfl/games');
const games = await response.json();

// Trigger refresh
await fetch('http://localhost:8080/api/nfl/refresh', { method: 'POST' });
```

## Troubleshooting

### Python Script Issues
- Ensure all Python dependencies are installed: `pip install -r scripts/requirements.txt`
- Check Python path in `application.properties`
- Verify nfl-data-py version is 0.3.3

### Database Issues
- Delete `nflweather.db` to reset database
- Check JPA logs in console for errors

### Docker Issues
- Ensure ports 8080 is available
- Check logs: `docker-compose logs -f backend`
- Rebuild: `docker-compose build --no-cache`

### No Games Returned
- Check if it's NFL season (September - February)
- Manually trigger refresh: `POST /api/nfl/refresh`
- Check Python script output in logs

## Development Notes

- **NFLGame Entity**: Main entity representing an NFL game
- **WeatherData Entity**: One-to-one relationship with NFLGame
- **NFLWeatherService**: Orchestrates Python script execution and data persistence
- **DataRefreshScheduler**: Handles scheduled daily refreshes
- **NFLWeatherController**: REST API endpoints

## Future Enhancements

- [ ] Add React frontend container to docker-compose
- [ ] Implement caching for frequently accessed data
- [ ] Add authentication/authorization
- [ ] Support for historical game data
- [ ] Real-time score updates
- [ ] Push notifications for weather alerts
- [ ] GraphQL API support

## License

MIT License

## Contributing

Pull requests are welcome! Please ensure tests pass and code follows existing patterns.

## Support

For issues or questions, please open a GitHub issue.
