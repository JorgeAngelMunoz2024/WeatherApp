#!/bin/bash
# Initialize SQLite database with schema

DB_PATH="/app/data/nflweather.db"
SCHEMA_PATH="/app/init/schema.sql"

echo "Checking if database needs initialization..."

if [ ! -f "$DB_PATH" ]; then
    echo "Database not found. Creating new database with schema..."
    sqlite3 "$DB_PATH" < "$SCHEMA_PATH"
    echo "Database initialized successfully!"
else
    echo "Database already exists at $DB_PATH"
fi

echo "Starting application..."
exec "$@"
