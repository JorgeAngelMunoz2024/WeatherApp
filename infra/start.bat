@echo off
REM Start the NFL Weather application stack (Dev Mode)

echo ==========================================
echo Starting NFL Weather Application (Dev Mode)
echo ==========================================

REM Navigate to script directory
cd /d "%~dp0"

REM Build images once if they don't exist, then start containers
echo Checking/building images...
docker-compose build

echo.
echo Starting containers...
docker-compose up

echo.
echo ==========================================
echo Application Started!
echo ==========================================
echo.
echo Services:
echo   - Frontend:     http://localhost:3000
echo   - Backend API:  http://localhost:8080
echo   - Health Check: http://localhost:8080/api/nfl/health
echo.
echo Useful commands:
echo   - View logs:    docker-compose logs -f
echo   - View backend: docker-compose logs -f backend
echo   - View frontend: docker-compose logs -f frontend
echo   - Stop:         docker-compose down
echo   - Restart:      docker-compose restart
echo ==========================================
pause
