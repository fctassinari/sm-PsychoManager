### Passo 1: Build da imagem
```bash
podman build -t psycho-web:latest .
```

### Passo 2: Executar o container
```bash
podman stop psycho-web
```
```bash
podman rm psycho-web
```
```bash
podman run -d --name psycho-web -p 8080:8080 --restart unless-stopped localhost/psycho-web:latest
