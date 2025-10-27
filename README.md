# PsychoManager - Sistema de Gestão de Consultório Psicológico

Sistema completo para gestão de consultório psicológico, migrado de Java 8 + JSF/PrimeFaces + Wildfly + MySQL para React 19.1 + Java 21 + Quarkus 3.28.3 + PostgreSQL 18.0.

## 🏗️ Arquitetura

### Backend
- **Java 21** com **Quarkus 3.28.3**
- **PostgreSQL 18.0** como banco de dados
- **JPA/Hibernate** para persistência
- **REST APIs** com validação
- **Flyway** para migração de banco
- **OpenAPI/Swagger** para documentação

### Frontend
- **React 19.1** com **TypeScript**
- **Material-UI (MUI)** para interface
- **React Router** para navegação
- **React Query** para gerenciamento de estado
- **React Hook Form** com validação Yup
- **Recharts** para gráficos
- **React Big Calendar** para agenda

## 🚀 Funcionalidades

### Gestão de Pacientes
- ✅ Cadastro completo com dados pessoais e médicos
- ✅ Múltiplos telefones por paciente
- ✅ Categorização por faixa de valor
- ✅ Upload de fichas em PDF
- ✅ Sistema de senhas de acesso

### Gestão de Produtos/Serviços
- ✅ Cadastro de serviços com 4 faixas de preço
- ✅ Categorização por tipo de atendimento
- ✅ Controle de preços dinâmico

### Sistema de Agendamento
- ✅ Calendário interativo (mês/semana/dia)
- ✅ Controle de presença
- ✅ Status de pagamento (A Receber, Pago, Abonado, Calote)
- ✅ Cálculo automático de preços por categoria
- ✅ Observações e anotações

### Relatórios e Dashboard
- ✅ Dashboard com estatísticas em tempo real
- ✅ Relatórios por período
- ✅ Gráficos de consultas por serviço
- ✅ Análise de status de pagamento
- ✅ Evolução mensal de atendimentos

## 📋 Pré-requisitos

### Backend
- Java 21+
- Maven 3.8+
- PostgreSQL 18.0+

### Frontend
- Node.js 18+
- npm ou yarn

## 🛠️ Instalação e Execução

### 1. Configuração do Banco de Dados

```bash
# Instalar PostgreSQL 18.0
# Criar banco de dados
createdb psycho_manager

# Criar usuário (opcional)
createuser -P psycho_manager
```

### 2. Configuração do Backend

```bash

# Configurar variáveis de ambiente (opcional)
$env:QUARKUS_DATASOURCE_JDBC_URL="jdbc:postgresql://localhost:5432/psycho_manager"
$env:QUARKUS_DATASOURCE_USERNAME="psycho_manager"
$env:QUARKUS_DATASOURCE_PASSWORD="psycho_manager"
$env:QUARKUS_HTTP_CORS_ORIGINS="http://localhost:3000,http://frontend:80"
$env:QUARKUS_HTTP_CORS_HEADERS="accept,authorization,content-type,x-requested-with"
$env:QUARKUS_HTTP_CORS_METHODS="GET,POST,PUT,DELETE,OPTIONS"
$env:PSYCHO_MANAGER_PDF_PATH="/app/uploads/pdfs"
$env:PSYCHO_MANAGER_TIMEZONE="America/Sao_Paulo"

# Executar o backend
./mvnw quarkus:dev
```

O backend estará disponível em: `http://localhost:8080`

### 3. Configuração do Frontend

```bash
# Navegar para o diretório do frontend
cd frontend

# Instalar dependências
npm install

# Configurar variáveis de ambiente (opcional)
echo "REACT_APP_API_URL=http://localhost:8080/api" > .env

# Executar o frontend
npm start
```

O frontend estará disponível em: `http://localhost:3000`

## 📚 Documentação da API

Acesse a documentação interativa da API em: `http://localhost:8080/swagger-ui`

## 🗄️ Estrutura do Banco de Dados

### Tabelas Principais

- **tb_paciente**: Dados dos pacientes
- **tb_fone**: Telefones dos pacientes
- **tb_produto**: Produtos/serviços oferecidos
- **tb_agenda**: Agendamentos de consultas
- **tb_senha**: Senhas de acesso dos pacientes
- **tb_aut_user**: Usuários do sistema
- **tb_aut_group**: Grupos de usuários

### Relacionamentos

- Paciente → Telefones (1:N)
- Paciente → Senha (1:1)
- Paciente → Agendamentos (1:N)
- Produto → Agendamentos (1:N)

## 🔧 Configurações

### Backend (application.properties)

```properties
# Database
quarkus.datasource.db-kind=postgresql
quarkus.datasource.username=psycho_manager
quarkus.datasource.password=psycho_manager
quarkus.datasource.jdbc.url=jdbc:postgresql://localhost:5432/psycho_manager

# CORS para React
quarkus.http.cors=true
quarkus.http.cors.origins=http://localhost:3000

# Upload de arquivos
quarkus.http.body-handler.uploads-directory=uploads
```

### Frontend (.env)

```env
REACT_APP_API_URL=http://localhost:8080/api
```

## 🚀 Deploy

### Backend (Produção)

```bash
# Build da aplicação
./mvnw clean package -Pnative

# Executar
./target/psycho-manager-1.0.0-SNAPSHOT-runner
```

### Frontend (Produção)

```bash
# Build da aplicação
npm run build

# Servir arquivos estáticos
npx serve -s build
```

## 📱 Funcionalidades Implementadas

### ✅ Gestão de Pacientes
- [x] CRUD completo de pacientes
- [x] Validação de dados com Yup
- [x] Múltiplos telefones
- [x] Categorização por valor
- [x] Upload de fichas PDF
- [x] Sistema de senhas

### ✅ Gestão de Produtos
- [x] CRUD de produtos/serviços
- [x] 4 faixas de preço por categoria
- [x] Validação de dados

### ✅ Sistema de Agendamento
- [x] Calendário interativo
- [x] Controle de presença
- [x] Status de pagamento
- [x] Cálculo automático de preços
- [x] Observações

### ✅ Relatórios
- [x] Dashboard com estatísticas
- [x] Gráficos interativos
- [x] Relatórios por período
- [x] Análise de pagamentos

### ✅ Interface
- [x] Design responsivo
- [x] Material-UI
- [x] Navegação intuitiva
- [x] Feedback visual
- [x] Validação em tempo real

## 🔒 Segurança

- Validação de dados no backend e frontend
- CORS configurado
- Sanitização de inputs
- Validação de tipos TypeScript

## 🧪 Testes

```bash
# Backend
./mvnw test

# Frontend
npm test
```

## 📈 Performance

- Queries otimizadas com índices
- Lazy loading de relacionamentos
- Cache de consultas no frontend
- Paginação em listas grandes

## 🤝 Contribuição

1. Fork o projeto
2. Crie uma branch para sua feature
3. Commit suas mudanças
4. Push para a branch
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo LICENSE para mais detalhes.

## 🆘 Suporte

Para suporte, entre em contato através dos issues do GitHub ou email: suporte@psychomanager.com

---

**Desenvolvido com ❤️ para profissionais da psicologia**