@echo off
REM Initialize SQLite database with schema

set "DB_PATH=%~dp0..\nfl-weather-backend\data\nflweather.db"
set "SCHEMA_PATH=%~dp0schema.sql"

echo Checking if database needs initialization...

if exist "%DB_PATH%" (
    echo Database already exists at %DB_PATH%
) else (
    echo Database not found. Creating new database with schema...
    
    REM Create data directory if it doesn't exist
    set "DATA_DIR=%~dp0..\nfl-weather-backend\data"
    if not exist "%DATA_DIR%" (
        mkdir "%DATA_DIR%"
    )
    
    REM Create database with schema
    if exist "%SCHEMA_PATH%" (
        sqlite3 "%DB_PATH%" < "%SCHEMA_PATH%"
        echo Database initialized successfully!
    ) else (
        echo Error: schema.sql not found at %SCHEMA_PATH%
        exit /b 1
    )
)

echo Starting application...
REM Execute any additional commands passed as arguments
%*
