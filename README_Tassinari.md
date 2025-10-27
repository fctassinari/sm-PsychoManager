# Banco de dados

```bash
podman volume create psycho_postgres_data
```
```bash
podman network create psycho-network
```
```bash
podman run --name psycho-postgres -p 5432:5432 --network psycho-network -e POSTGRES_DB=psycho_manager -e POSTGRES_USER=psycho_manager -e POSTGRES_PASSWORD=psycho_manager  --volume psycho_postgres_data:/var/lib/postgresql  -d postgres:18.0
```

# Backend
```bash
podman volume create uploads_data
```
```bash
cd psycho-api
```
```bash
./mvnw package
```
```bash
podman build -t psycho-api:latest .
```
```bash
podman stop psycho-api
```
```bash
podman rm psycho-api
```
```bash
podman run -d --name psycho-api -p 8081:8081 --network psycho-network  --volume uploads_data:/app/uploads --restart unless-stopped psycho-api:latest
```

# Frontend 
```bash
cd ../frontend
```
```bash
podman build -t psycho-web:latest .
```
```bash
podman stop psycho-web
```
```bash
podman rm psycho-web
```
```bash
podman run -d --name psycho-web -p 8080:8080 --network psycho-network --restart unless-stopped localhost/psycho-web:latest
```


