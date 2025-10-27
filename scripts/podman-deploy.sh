#!/bin/bash

# Script de deploy da aplicação usando Podman
# Uso: ./scripts/podman-deploy.sh [dev|prod] [start|stop|restart|logs|status]

set -e

ENVIRONMENT=${1:-dev}
ACTION=${2:-start}

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

# Função para warning
warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

# Verificar se Podman está rodando
if ! podman info > /dev/null 2>&1; then
    error "Podman não está rodando. Por favor, inicie o Podman e tente novamente."
fi

# Definir arquivo de compose baseado no ambiente
if [ "$ENVIRONMENT" = "prod" ]; then
    COMPOSE_FILE="podman-compose.prod.yml"
    log "Usando configuração de produção"
else
    COMPOSE_FILE="podman-compose.yml"
    log "Usando configuração de desenvolvimento"
fi

# Verificar se o arquivo existe
if [ ! -f "$COMPOSE_FILE" ]; then
    error "Arquivo $COMPOSE_FILE não encontrado"
fi

# Função para iniciar serviços
start_services() {
    log "Iniciando serviços..."
    
    # Parar serviços existentes se estiverem rodando
    podman compose -f $COMPOSE_FILE down 2>/dev/null || true
    
    # Iniciar serviços
    if podman compose -f $COMPOSE_FILE up -d; then
        success "Serviços iniciados com sucesso"
        
        # Aguardar serviços ficarem saudáveis
        log "Aguardando serviços ficarem saudáveis..."
        sleep 10
        
        # Mostrar status
        show_status
    else
        error "Falha ao iniciar serviços"
    fi
}

# Função para parar serviços
stop_services() {
    log "Parando serviços..."
    if podman compose -f $COMPOSE_FILE down; then
        success "Serviços parados com sucesso"
    else
        error "Falha ao parar serviços"
    fi
}

# Função para reiniciar serviços
restart_services() {
    log "Reiniciando serviços..."
    stop_services
    sleep 5
    start_services
}

# Função para mostrar logs
show_logs() {
    log "Mostrando logs dos serviços..."
    podman compose -f $COMPOSE_FILE logs -f
}

# Função para mostrar status
show_status() {
    log "Status dos serviços:"
    echo ""
    podman compose -f $COMPOSE_FILE ps
    echo ""
    
    # Verificar saúde dos serviços
    log "Verificando saúde dos serviços..."
    
    # PostgreSQL
    if podman exec psycho-postgres pg_isready -U psycho_manager -d psycho_manager > /dev/null 2>&1; then
        success "PostgreSQL: OK"
    else
        warning "PostgreSQL: Não está respondendo"
    fi
    
    # Backend
    if curl -f http://localhost:8080/q/health > /dev/null 2>&1; then
        success "Backend: OK"
    else
        warning "Backend: Não está respondendo"
    fi
    
    # Frontend
    if curl -f http://localhost:3000 > /dev/null 2>&1; then
        success "Frontend: OK"
    else
        warning "Frontend: Não está respondendo"
    fi
    
    echo ""
    echo "🌐 URLs de acesso:"
    echo "  Frontend: http://localhost:3000"
    echo "  Backend API: http://localhost:8080"
    echo "  Swagger UI: http://localhost:8080/swagger-ui"
    echo "  Health Check: http://localhost:8080/q/health"
}

# Função para backup do banco
backup_database() {
    log "Fazendo backup do banco de dados..."
    
    # Criar diretório de backup se não existir
    mkdir -p ./backups
    
    # Fazer backup
    BACKUP_FILE="./backups/backup_$(date +%Y%m%d_%H%M%S).sql"
    
    if podman exec psycho-postgres pg_dump -U psycho_manager psycho_manager > $BACKUP_FILE; then
        success "Backup criado: $BACKUP_FILE"
    else
        error "Falha ao criar backup"
    fi
}

# Função para restore do banco
restore_database() {
    BACKUP_FILE=$1
    
    if [ -z "$BACKUP_FILE" ]; then
        error "Arquivo de backup não especificado"
    fi
    
    if [ ! -f "$BACKUP_FILE" ]; then
        error "Arquivo de backup não encontrado: $BACKUP_FILE"
    fi
    
    log "Restaurando banco de dados de: $BACKUP_FILE"
    
    if podman exec -i psycho-postgres psql -U psycho_manager -d psycho_manager < $BACKUP_FILE; then
        success "Banco de dados restaurado com sucesso"
    else
        error "Falha ao restaurar banco de dados"
    fi
}

# Função para mostrar ajuda
show_help() {
    echo "Uso: $0 [ENVIRONMENT] [ACTION]"
    echo ""
    echo "ENVIRONMENT:"
    echo "  dev   - Ambiente de desenvolvimento (padrão)"
    echo "  prod  - Ambiente de produção"
    echo ""
    echo "ACTION:"
    echo "  start     - Iniciar serviços (padrão)"
    echo "  stop      - Parar serviços"
    echo "  restart   - Reiniciar serviços"
    echo "  logs      - Mostrar logs"
    echo "  status    - Mostrar status dos serviços"
    echo "  backup    - Fazer backup do banco"
    echo "  restore   - Restaurar banco (especificar arquivo)"
    echo "  help      - Mostrar esta ajuda"
    echo ""
    echo "Exemplos:"
    echo "  $0 dev start"
    echo "  $0 prod restart"
    echo "  $0 dev logs"
    echo "  $0 prod backup"
    echo "  $0 prod restore ./backups/backup_20240101_120000.sql"
}

# Executar ação baseada no parâmetro
case $ACTION in
    start)
        start_services
        ;;
    stop)
        stop_services
        ;;
    restart)
        restart_services
        ;;
    logs)
        show_logs
        ;;
    status)
        show_status
        ;;
    backup)
        backup_database
        ;;
    restore)
        restore_database $3
        ;;
    help|--help|-h)
        show_help
        ;;
    *)
        error "Ação inválida: $ACTION. Use 'help' para ver as opções disponíveis."
        ;;
esac
