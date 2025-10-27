#!/bin/bash

# Script para build das imagens Docker
# Uso: ./scripts/build-images.sh [dev|prod]

set -e

ENVIRONMENT=${1:-dev}
VERSION=${2:-latest}

echo "🔨 Building Docker images for environment: $ENVIRONMENT"
echo "📦 Version: $VERSION"

# Cores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Função para log
log() {
    echo -e "${BLUE}[$(date +'%Y-%m-%d %H:%M:%S')]${NC} $1"
}

# Função para erro
error() {
    echo -e "${RED}[ERROR]${NC} $1"
    exit 1
}

# Função para sucesso
success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

# Verificar se Docker está rodando
if ! docker info > /dev/null 2>&1; then
    error "Docker não está rodando. Por favor, inicie o Docker e tente novamente."
fi

# Build do Backend
log "Building backend image..."
if docker build -t psycho-manager-backend:$VERSION .; then
    success "Backend image built successfully"
else
    error "Failed to build backend image"
fi

# Build do Frontend
log "Building frontend image..."
if docker build -t psycho-manager-frontend:$VERSION ./frontend; then
    success "Frontend image built successfully"
else
    error "Failed to build frontend image"
fi

# Listar imagens criadas
log "Created images:"
docker images | grep psycho-manager

# Se for produção, criar tags adicionais
if [ "$ENVIRONMENT" = "prod" ]; then
    log "Creating production tags..."
    docker tag psycho-manager-backend:$VERSION psycho-manager-backend:prod
    docker tag psycho-manager-frontend:$VERSION psycho-manager-frontend:prod
    success "Production tags created"
fi

success "All images built successfully! 🎉"
echo ""
echo "Para executar a aplicação:"
echo "  Desenvolvimento: docker-compose up -d"
echo "  Produção: docker-compose -f docker-compose.prod.yml up -d"
echo ""
echo "Para parar a aplicação:"
echo "  docker-compose down"
