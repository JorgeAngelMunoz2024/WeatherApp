#!/bin/bash
# Build and start the NFL Weather application stack

set -e

echo "=========================================="
echo "Building NFL Weather Application"
echo "=========================================="

# Navigate to infra directory
cd "$(dirname "$0")"

# Build and start containers
echo "Building Docker images..."
docker-compose build

echo ""
echo "Starting containers..."
docker-compose up -d

echo ""
echo "=========================================="
echo "Application Started Successfully!"
echo "=========================================="
echo ""
echo "Services:"
echo "  - Backend API: http://localhost:8080"
echo "  - Health Check: http://localhost:8080/api/nfl/health"
echo ""
echo "Useful commands:"
echo "  - View logs: docker-compose logs -f"
echo "  - Stop: docker-compose down"
echo "  - Restart: docker-compose restart"
echo "=========================================="
