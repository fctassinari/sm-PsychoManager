# 🐳 Configuração Docker - PsychoManager

Este guia explica como configurar e executar a aplicação PsychoManager usando Docker.

## 📋 Pré-requisitos

- Docker Desktop instalado e rodando
- Docker Compose v2.0+
- Git (para clonar o repositório)

## 🚀 Execução Rápida

### 1. Desenvolvimento

```bash
# Clonar o repositório
git clone <seu-repositorio>
cd sm-PsychoManager

# Executar em modo desenvolvimento
podman compose up -d

# Verificar status
podman compose ps

# Ver logs
podman compose logs -f
```

### 2. Produção

```bash
# Executar em modo produção
podman compose -f podman compose.prod.yml up -d

# Verificar status
podman compose -f podman compose.prod.yml ps
```

## 🔧 Configuração Detalhada

### Estrutura dos Arquivos Docker

```
sm-PsychoManager/
├── Dockerfile                    # Backend Quarkus
├── podman compose.yml           # Desenvolvimento
├── podman compose.prod.yml      # Produção
├── nginx.conf                   # Proxy reverso (desenvolvimento)
├── nginx.prod.conf              # Proxy reverso (produção)
├── init-db.sql                  # Inicialização do banco
├── .dockerignore                # Arquivos ignorados no build
├── frontend/
│   ├── Dockerfile               # Frontend React
│   ├── nginx.conf               # Configuração Nginx para frontend
│   └── .dockerignore            # Arquivos ignorados no build
└── scripts/
    ├── build-images.sh          # Build das imagens
    ├── deploy.sh                # Deploy da aplicação
    └── setup-ssl.sh             # Configuração SSL
```

### Serviços Incluídos

1. **PostgreSQL 18.0** - Banco de dados
2. **Backend Quarkus** - APIs REST
3. **Frontend React** - Interface web
4. **Nginx** - Proxy reverso (opcional)

## 🛠️ Comandos Úteis

### Desenvolvimento

```bash
# Iniciar todos os serviços
podman compose up -d

# Iniciar apenas o banco
podman compose up -d postgres

# Iniciar apenas o backend
podman compose up -d backend

# Iniciar apenas o frontend
podman compose up -d frontend


# Ver logs de um serviço específico
podman compose logs -f backend
podman compose logs -f frontend
podman compose logs -f postgres

# Parar todos os serviços
podman compose down

# Parar e remover volumes
podman compose down -v

# Rebuild das imagens
podman compose build --no-cache

# Executar comandos no container
podman compose exec backend bash
podman compose exec postgres psql -U psycho_manager -d psycho_manager
```

### Produção

```bash
# Iniciar em produção
podman compose -f podman compose.prod.yml up -d

# Ver logs
podman compose -f podman compose.prod.yml logs -f

# Parar
podman compose -f podman compose.prod.yml down

# Backup do banco
podman compose -f podman compose.prod.yml exec postgres pg_dump -U psycho_manager psycho_manager > backup.sql

# Restore do banco
podman compose -f podman compose.prod.yml exec -T postgres psql -U psycho_manager psycho_manager < backup.sql
```

## 🔐 Configuração SSL

### Desenvolvimento

```bash
# Gerar certificado auto-assinado
./scripts/setup-ssl.sh dev
```

### Produção

```bash
# Configurar SSL para produção
./scripts/setup-ssl.sh prod

# Ou usar Let's Encrypt
sudo certbot certonly --standalone -d seu-dominio.com
sudo cp /etc/letsencrypt/live/seu-dominio.com/fullchain.pem ssl/cert.pem
sudo cp /etc/letsencrypt/live/seu-dominio.com/privkey.pem ssl/key.pem
```

## 📊 Monitoramento

### Health Checks

- **PostgreSQL**: `docker exec psycho-postgres pg_isready -U psycho_manager -d psycho_manager`
- **Backend**: `curl http://localhost:8080/q/health`
- **Frontend**: `curl http://localhost:3000`

### URLs de Acesso

- **Frontend**: http://localhost:3000
- **Backend API**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui
- **Health Check**: http://localhost:8080/q/health

## 🗄️ Gerenciamento de Dados

### Backup

```bash
# Backup manual
podman compose exec postgres pg_dump -U psycho_manager psycho_manager > backup_$(date +%Y%m%d_%H%M%S).sql

# Backup automático (produção)
# O container de backup executa diariamente às 00:00
```

### Restore

```bash
# Restore de backup
podman compose exec -T postgres psql -U psycho_manager psycho_manager < backup.sql
```

### Limpeza

```bash
# Remover containers parados
docker container prune

# Remover imagens não utilizadas
docker image prune

# Remover volumes não utilizados
docker volume prune

# Limpeza completa
docker system prune -a
```

## 🐛 Troubleshooting

### Problemas Comuns

1. **Porta já em uso**
   ```bash
   # Verificar portas em uso
   netstat -tulpn | grep :3000
   netstat -tulpn | grep :8080
   netstat -tulpn | grep :5432
   
   # Parar serviços conflitantes
   sudo systemctl stop postgresql
   ```

2. **Container não inicia**
   ```bash
   # Ver logs detalhados
   podman compose logs backend
   podman compose logs frontend
   podman compose logs postgres
   ```

3. **Banco não conecta**
   ```bash
   # Verificar se PostgreSQL está rodando
   podman compose exec postgres pg_isready -U psycho_manager -d psycho_manager
   
   # Verificar logs do banco
   podman compose logs postgres
   ```

4. **Frontend não carrega**
   ```bash
   # Verificar se backend está rodando
   curl http://localhost:8080/q/health
   
   # Verificar logs do frontend
   podman compose logs frontend
   ```

### Reset Completo

```bash
# Parar todos os containers
podman compose down

# Remover volumes
podman compose down -v

# Remover imagens
docker rmi psycho-manager-backend psycho-manager-frontend

# Rebuild completo
podman compose build --no-cache
podman compose up -d
```

## 📈 Performance

### Otimizações Recomendadas

1. **PostgreSQL**
   - Ajustar `shared_buffers` e `effective_cache_size`
   - Configurar `max_connections` adequadamente

2. **Backend Quarkus**
   - Usar JVM otimizada para containers
   - Configurar heap size adequadamente

3. **Frontend React**
   - Usar build otimizado para produção
   - Configurar cache adequadamente

### Monitoramento

```bash
# Uso de recursos
docker stats

# Logs em tempo real
podman compose logs -f

# Status dos serviços
podman compose ps
```

## 🔒 Segurança

### Configurações de Segurança

1. **Nginx**
   - Headers de segurança configurados
   - Rate limiting habilitado
   - SSL/TLS configurado

2. **PostgreSQL**
   - Usuário com privilégios limitados
   - Conexões restritas à rede interna

3. **Backend**
   - CORS configurado
   - Validação de dados
   - Sanitização de inputs

## 📝 Logs

### Localização dos Logs

- **Aplicação**: `./logs/`
- **Nginx**: `./logs/nginx/`
- **PostgreSQL**: `podman compose logs postgres`

### Rotação de Logs

```bash
# Configurar logrotate para logs da aplicação
sudo nano /etc/logrotate.d/psycho-manager
```

## 🚀 Deploy em Produção

### Checklist de Deploy

- [ ] Certificados SSL configurados
- [ ] Variáveis de ambiente configuradas
- [ ] Backup do banco realizado
- [ ] Monitoramento configurado
- [ ] Logs centralizados
- [ ] Backup automático configurado

### Variáveis de Ambiente

```bash
# .env para produção
POSTGRES_PASSWORD=senha_super_segura
FRONTEND_URL=https://seu-dominio.com
```

## 📞 Suporte

Para problemas ou dúvidas:

1. Verificar logs: `podman compose logs -f`
2. Verificar status: `podman compose ps`
3. Verificar saúde: `curl http://localhost:8080/q/health`
4. Abrir issue no GitHub

---

**Desenvolvido com ❤️ para profissionais da psicologia**
