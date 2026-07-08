# 💰 Calculadora de Empréstimos

Aplicação desenvolvida como solução para desafio técnico.

O projeto consiste em uma calculadora de empréstimos composta por:

- Backend REST em Java + Spring Boot
- Frontend em React + TypeScript
- Interface baseada na planilha de referência disponibilizada no desafio

---

# Tecnologias

## Backend

- Java 17
- Spring Boot
- Maven
- Spring Validation
- Springdoc OpenAPI (Swagger)
- JUnit 5

## Frontend

- React
- TypeScript
- Vite
- Material UI
- Axios
- React Number Format

---

# Estrutura do projeto

```
loan-calculator
│
├── backend
│   ├── src
│   ├── pom.xml
│
├── frontend
│   ├── src
│   ├── package.json
│
└── README.md
```

---

# Funcionalidades

- Cadastro dos parâmetros do empréstimo
- Geração automática das datas de competência
- Cálculo de amortização
- Cálculo de saldo devedor
- Cálculo de juros provisionados
- Cálculo de juros acumulados
- Cálculo do valor pago
- Exibição dos resultados em formato de tabela semelhante à planilha do desafio

---

# Validações implementadas

Backend:

- Data final deve ser maior que a data inicial.
- Primeiro pagamento deve ser posterior à data inicial.
- Primeiro pagamento deve ser anterior à data final.
- Datas inválidas são rejeitadas automaticamente pelo Java (ex.: 31/02/2026).

Frontend:

- Campos obrigatórios.
- Máscara monetária.
- Máscara para taxa de juros.
- Botão de cálculo habilitado apenas quando os campos obrigatórios forem preenchidos.

---

# Executando o Backend

Entre na pasta:

```bash
cd backend
```

Execute:

```bash
# Windows PowerShell
.\mvnw.cmd spring-boot:run
```

Ou

```bash
# Linux/macOS/Git Bash
./mvnw spring-boot:run
```

Swagger:

```
http://localhost:8080/swagger-ui/index.html
```

---

# Executando o Frontend

Entre na pasta:

```bash
cd frontend
```

Instale as dependências:

```bash
npm install
```

Execute:

```bash
npm run dev
```

Aplicação:

```
http://localhost:5173
```

---

# Testes

Backend:

```bash
# Windows PowerShell
.\mvnw.cmd test
```

Ou

```bash
# Linux/macOS/Git Bash
./mvnw test
```

Os testes contemplam:

- Service
- Controller
- CompetenceDateGenerator
- InterestCalculator

---

# Arquitetura

O backend foi organizado em camadas para facilitar manutenção e evolução.

```
Controller
        │
        ▼
Service
        │
        ▼
Domain
        │
        ▼
DTOs
```

O algoritmo financeiro foi dividido em componentes especializados:

- CompetenceDateGenerator
- InstallmentGenerator
- InterestCalculator

Essa separação reduz o acoplamento e facilita os testes unitários.

---

# Decisões técnicas

- Utilização de BigDecimal para evitar perda de precisão em operações financeiras.
- Separação do algoritmo financeiro em classes específicas.
- Testes unitários cobrindo regras de negócio.
- Swagger para documentação da API.
- React Number Format para entrada de valores monetários.
- Material UI para padronização visual.

---

# Demonstração

## Tela principal

<img width="1875" height="827" alt="image" src="https://github.com/user-attachments/assets/d29c9678-a32a-4eee-bb55-3e774f522dfd" />


---

# Autor

**Edvan Junior**
