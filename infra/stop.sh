#!/bin/bash
# Stop the NFL Weather application stack

cd "$(dirname "$0")"

echo "Stopping NFL Weather Application..."
docker-compose down

echo "Application stopped successfully!"
