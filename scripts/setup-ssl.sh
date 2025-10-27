#!/bin/bash

# Script para configurar certificados SSL
# Uso: ./scripts/setup-ssl.sh [dev|prod]

set -e

ENVIRONMENT=${1:-dev}

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

# Criar diretório SSL
mkdir -p ssl

if [ "$ENVIRONMENT" = "dev" ]; then
    log "Configurando SSL para desenvolvimento..."
    
    # Gerar certificado auto-assinado para desenvolvimento
    if [ ! -f "ssl/cert.pem" ] || [ ! -f "ssl/key.pem" ]; then
        log "Gerando certificado auto-assinado..."
        
        openssl req -x509 -newkey rsa:4096 -keyout ssl/key.pem -out ssl/cert.pem -days 365 -nodes \
            -subj "/C=BR/ST=SP/L=SaoPaulo/O=PsychoManager/OU=Development/CN=localhost" \
            -addext "subjectAltName=DNS:localhost,DNS:*.localhost,IP:127.0.0.1,IP:0.0.0.0"
        
        success "Certificado auto-assinado gerado"
        warning "Este certificado é apenas para desenvolvimento. Para produção, use certificados válidos."
    else
        log "Certificados já existem"
    fi
    
elif [ "$ENVIRONMENT" = "prod" ]; then
    log "Configurando SSL para produção..."
    
    if [ ! -f "ssl/cert.pem" ] || [ ! -f "ssl/key.pem" ]; then
        warning "Certificados SSL não encontrados para produção!"
        echo ""
        echo "Para produção, você precisa de certificados SSL válidos."
        echo "Opções:"
        echo "1. Usar Let's Encrypt com certbot"
        echo "2. Usar certificados de uma CA confiável"
        echo "3. Usar um proxy reverso como Cloudflare"
        echo ""
        echo "Para usar Let's Encrypt:"
        echo "  sudo apt-get install certbot"
        echo "  sudo certbot certonly --standalone -d seu-dominio.com"
        echo "  sudo cp /etc/letsencrypt/live/seu-dominio.com/fullchain.pem ssl/cert.pem"
        echo "  sudo cp /etc/letsencrypt/live/seu-dominio.com/privkey.pem ssl/key.pem"
        echo "  sudo chown \$USER:\$USER ssl/cert.pem ssl/key.pem"
        echo ""
        
        read -p "Deseja gerar um certificado auto-assinado temporário? (y/N): " -n 1 -r
        echo
        if [[ $REPLY =~ ^[Yy]$ ]]; then
            log "Gerando certificado auto-assinado temporário..."
            
            openssl req -x509 -newkey rsa:4096 -keyout ssl/key.pem -out ssl/cert.pem -days 30 -nodes \
                -subj "/C=BR/ST=SP/L=SaoPaulo/O=PsychoManager/OU=Production/CN=localhost" \
                -addext "subjectAltName=DNS:localhost,DNS:*.localhost,IP:127.0.0.1,IP:0.0.0.0"
            
            success "Certificado auto-assinado temporário gerado (válido por 30 dias)"
            warning "IMPORTANTE: Substitua por certificados válidos antes de usar em produção!"
        else
            error "Certificados SSL são obrigatórios para produção"
        fi
    else
        log "Certificados SSL encontrados"
    fi
    
else
    error "Ambiente inválido: $ENVIRONMENT. Use 'dev' ou 'prod'"
fi

# Verificar permissões
chmod 600 ssl/key.pem
chmod 644 ssl/cert.pem

success "Configuração SSL concluída!"
echo ""
echo "Certificados localizados em:"
echo "  Certificado: ssl/cert.pem"
echo "  Chave privada: ssl/key.pem"
echo ""
echo "Para verificar o certificado:"
echo "  openssl x509 -in ssl/cert.pem -text -noout"
