🚀 COMO EXECUTAR AGORA:
Desenvolvimento (Recomendado)
```
# Usar o arquivo específico para Podman
podman compose -f podman-compose.yml up -d

# Ou usar o script automatizado
./scripts/podman-deploy.sh dev start
```
Verificar Status
```
# Status dos containers
podman compose -f podman-compose.yml ps

# Logs em tempo real
podman compose -f podman-compose.yml logs -f
```
🎯 PRÓXIMOS PASSOS:
Postgres:
```
   podman compose -f podman-compose.yml up -d postgres
```
Backend:
```
   podman compose -f podman-compose.yml up -d backend
```
Frontend:
```
   podman compose -f podman-compose.yml up -d frontend
```
Acesse a aplicação:
Frontend: http://localhost:8080
Backend: http://localhost:8081
Swagger: http://localhost:8081/swagger-ui
Agora deve funcionar perfeitamente com Podman! 🎉
