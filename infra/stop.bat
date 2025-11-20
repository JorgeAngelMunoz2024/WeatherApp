@echo off
REM Stop the NFL Weather application stack

cd /d "%~dp0"

echo Stopping NFL Weather Application...
docker-compose down

echo Application stopped successfully!
pause
