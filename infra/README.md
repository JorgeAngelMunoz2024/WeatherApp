# NFL Weather Application - Infrastructure

This directory contains Docker infrastructure for running the NFL Weather application in development mode.

## Quick Start

### Windows
```bash
start.bat
```

### Linux/Mac
```bash
chmod +x start.sh
./start.sh
```

## Services

- **Backend API**: http://localhost:8080
- **Health Check**: http://localhost:8080/api/nfl/health

## Available Commands

### Start Application
- Windows: `start.bat`
- Linux/Mac: `./start.sh`

### Stop Application
- Windows: `stop.bat`
- Linux/Mac: `./stop.sh`

### View Logs
```bash
docker-compose logs -f
```

### View Backend Logs Only
```bash
docker-compose logs -f backend
```

### Restart Services
```bash
docker-compose restart
```

### Rebuild Containers
```bash
docker-compose build --no-cache
docker-compose up -d
```

### Clean Up Everything
```bash
docker-compose down -v
```

## API Endpoints

Once running, you can access:

- `GET /api/nfl/games` - Get all NFL games
- `GET /api/nfl/games/{gameId}` - Get specific game
- `GET /api/nfl/games/week/{week}` - Get games by week
- `GET /api/nfl/games/season/{season}/week/{week}` - Get games by season and week
- `GET /api/nfl/games/team/{team}` - Get games for a specific team
- `POST /api/nfl/refresh` - Manually refresh game data
- `GET /api/nfl/health` - Health check endpoint

## Volumes

The application uses Docker volumes to persist data:

- `backend-data`: Application data
- `backend-db`: SQLite database

## Environment Variables

The backend service runs with:
- `SPRING_PROFILES_ACTIVE=dev` - Development profile
- `PYTHON_EXECUTABLE=python3` - Python interpreter for scripts

## Troubleshooting

### Check if containers are running
```bash
docker-compose ps
```

### Check backend logs for errors
```bash
docker-compose logs backend
```

### Restart backend service
```bash
docker-compose restart backend
```

### Access backend container shell
```bash
docker-compose exec backend bash
```

## Notes

- This setup is configured for **local development** on localhost
- No nginx reverse proxy is configured
- Database and data persist in Docker volumes
- Backend runs on port 8080
- Frontend placeholder is commented out (ready for future React app on port 3000)
