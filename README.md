# 🛒 Pretium - Sistema de Rastreamento de Preços em Supermercados

## 📋 Visão Geral

**Pretium** é um sistema completo de rastreamento, comparação e análise de preços de produtos em supermercados. Permite armazenar dados de cupons, comparar preços entre diferentes lojas, cidades, estados e países, além de gerar relatórios e análises de tendência de preços.

### Funcionalidades Principais

✅ **Gerenciamento de Dados**
- Cadastro de localizações (países, estados, cidades)
- Gerenciamento de supermercados
- Cadastro de produtos
- Registro de cupons e itens de compra

✅ **Análise e Comparação de Preços**
- Análise completa de preços (mínimo, máximo, média, mediana)
- Comparação de preços entre lojas de uma mesma cidade
- Comparação de preços entre lojas de um mesmo estado
- Comparação de preços entre lojas de um mesmo país
- Histórico de preços com série temporal

✅ **Relatórios**
- Identificação das lojas mais baratas e mais caras
- Cálculo de economia potencial
- Análise de variação de preços
- Tracking de promoções e descontos

## 🏗️ Arquitetura

```
Pretium/
├── entity/                 # Entidades JPA/Hibernate
│   ├── Location           # Localização geográfica
│   ├── Store              # Supermercado
│   ├── Product            # Produto
│   ├── Receipt            # Cupom de compra
│   ├── PurchaseItem       # Item de compra
│   └── PriceHistory       # Histórico de preços
├── repository/            # Camada de dados (JPA)
├── service/               # Lógica de negócio
├── controller/            # Controladores REST
├── dto/                   # Data Transfer Objects
└── config/                # Configurações
```

## 🗄️ Modelo de Dados

### Entidades Principais

#### Location (Localização)
```java
- id: Long (PK)
- country: String
- state: String
- city: String
- zipCode: String
- latitude: Double
- longitude: Double
```

#### Store (Supermercado)
```java
- id: Long (PK)
- name: String
- description: String
- phone: String
- website: String
- location: Location (FK)
- status: String (ACTIVE, INACTIVE, CLOSED)
- createdAt: LocalDateTime
- updatedAt: LocalDateTime
```

#### Product (Produto)
```java
- id: Long (PK)
- name: String
- barcode: String
- category: String
- brand: String
- unit: String (KG, L, UN, etc)
- weight: Double
- description: String
- status: String (ACTIVE, INACTIVE, DISCONTINUED)
- createdAt: LocalDateTime
- updatedAt: LocalDateTime
```

#### Receipt (Cupom)
```java
- id: Long (PK)
- store: Store (FK)
- receiptNumber: String (UNIQUE)
- purchaseDate: LocalDateTime
- totalAmount: BigDecimal
- discountAmount: BigDecimal
- taxAmount: BigDecimal
- paymentMethod: String
- notes: String
- status: String (ACTIVE, CANCELLED, ARCHIVED)
- createdAt: LocalDateTime
- updatedAt: LocalDateTime
```

#### PurchaseItem (Item de Compra)
```java
- id: Long (PK)
- receipt: Receipt (FK)
- product: Product (FK)
- quantity: Integer
- unitPrice: BigDecimal
- totalPrice: BigDecimal
- discountAmount: BigDecimal
- taxAmount: BigDecimal
- itemNumber: String
```

#### PriceHistory (Histórico de Preços)
```java
- id: Long (PK)
- product: Product (FK)
- store: Store (FK)
- price: BigDecimal
- discountedPrice: BigDecimal
- priceType: String (REGULAR, PROMOTION, SALE)
- promotionDescription: String
- recordedDate: LocalDateTime
- createdAt: LocalDateTime
```

## 🌐 API REST Endpoints

### 📍 Localizações

#### Criar Localização
```http
POST /api/locations
Content-Type: application/json

{
  "country": "Brasil",
  "state": "São Paulo",
  "city": "São Paulo",
  "zipCode": "01310100",
  "latitude": -23.5505,
  "longitude": -46.6333
}
```

#### Obter Localização por ID
```http
GET /api/locations/{id}
```

#### Listar Todas as Localizações
```http
GET /api/locations
```

#### Obter Localizações por País
```http
GET /api/locations/country/{country}
```

#### Obter Localizações por País e Estado
```http
GET /api/locations/country/{country}/state/{state}
```

#### Obter Localização Específica
```http
GET /api/locations/country/{country}/state/{state}/city/{city}
```

#### Atualizar Localização
```http
PUT /api/locations/{id}
Content-Type: application/json

{
  "country": "Brasil",
  "state": "São Paulo",
  "city": "São Paulo",
  ...
}
```

#### Deletar Localização
```http
DELETE /api/locations/{id}
```

### 💰 Análise e Comparação de Preços

#### Análise Completa de Preços de um Produto
```http
GET /api/prices/analysis/{productId}

Response:
{
  "productId": 1,
  "productName": "Arroz Integral 5kg",
  "category": "Alimentos Secos",
  "minPrice": 26.50,
  "maxPrice": 29.90,
  "averagePrice": 28.46,
  "medianPrice": 28.99,
  "totalStores": 3,
  "totalRecords": 3,
  "cheapestStore": "Mercado Central",
  "mostExpensiveStore": "Supermercado Carioca",
  "priceRange": 3.40,
  "pricesByStore": [
    {
      "storeName": "Mercado Central",
      "price": 26.50,
      "city": "São Paulo",
      "state": "São Paulo",
      "country": "Brasil"
    },
    ...
  ]
}
```

#### Comparar Preços por Cidade
```http
GET /api/prices/compare/city/{productId}/{city}

Exemplo: GET /api/prices/compare/city/1/São Paulo

Response: [
  {
    "productId": 1,
    "productName": "Arroz Integral 5kg",
    "storeId": 1,
    "storeName": "Supermercado Universal",
    "price": 28.99,
    "recordedDate": "2024-10-26T10:30:00"
  },
  ...
]
```

#### Comparar Preços por Estado
```http
GET /api/prices/compare/state/{productId}/{state}

Exemplo: GET /api/prices/compare/state/1/São Paulo
```

#### Comparar Preços por País
```http
GET /api/prices/compare/country/{productId}/{country}

Exemplo: GET /api/prices/compare/country/1/Brasil
```

#### Histórico de Preços (Série Temporal)
```http
GET /api/prices/history/{productId}?startDate=2024-01-01T00:00:00&endDate=2024-12-31T23:59:59

Response: [
  {
    "productId": 1,
    "productName": "Arroz Integral 5kg",
    "storeId": 1,
    "storeName": "Supermercado Universal",
    "price": 28.99,
    "priceType": "REGULAR",
    "recordedDate": "2024-10-26T10:30:00"
  },
  ...
]
```

## 🚀 Como Começar

### Pré-requisitos
- Java 25 ou superior
- Maven 3.8.9 ou superior
- Git

### Instalação

1. **Clone o repositório**
```bash
git clone https://github.com/seu-usuario/pretium.git
cd Pretium
```

2. **Build do projeto**
```bash
mvn clean install
```

3. **Execute a aplicação**
```bash
mvn spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8081`

### Acesso ao Console H2 (Banco de Dados em Memória)
```
URL: http://localhost:8081/h2-console
JDBC URL: jdbc:h2:mem:pretium
Username: sa
Password: (deixar em branco)
```

## 📊 Exemplos de Uso

### Exemplo 1: Encontrar o Supermercado Mais Barato para um Produto
```bash
# Analisar preços do produto ID 1
curl "http://localhost:8081/api/prices/analysis/1"

# Resultado mostrará a loja mais barata (cheapestStore)
```

### Exemplo 2: Comparar Preços em uma Cidade
```bash
# Comparar preços do produto 1 na cidade de São Paulo
curl "http://localhost:8081/api/prices/compare/city/1/São Paulo"

# Retorna lista ordenada por preço (menor para maior)
```

### Exemplo 3: Analisar Tendência de Preços
```bash
# Obter histórico de preços em um período
curl "http://localhost:8081/api/prices/history/1?startDate=2024-01-01T00:00:00&endDate=2024-12-31T23:59:59"
```

## 🔧 Configuração

### Banco de Dados

Atualmente, a aplicação usa H2 (banco em memória) para desenvolvimento. Para usar PostgreSQL em produção:

**application-prod.yml:**
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/pretium
    username: postgres
    password: sua_senha
    driver-class-name: org.postgresql.Driver
  
  jpa:
    database-platform: org.hibernate.dialect.PostgreSQLDialect
    hibernate:
      ddl-auto: validate
```

**Execute:**
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=prod"
```

## 📈 Próximas Funcionalidades

- [ ] Integração com OCR para leitura automática de cupons
- [ ] Dashboard web interativo com gráficos dinâmicos
- [ ] API de relatórios em PDF
- [ ] Notificações de queda de preço
- [ ] Machine Learning para previsão de preços
- [ ] Integração com APIs de supermercados para dados em tempo real
- [ ] Aplicativo mobile
- [ ] Autenticação e autorização (Spring Security)
- [ ] Caching com Redis

## 🤝 Contribuindo

Contribuições são bem-vindas! Por favor:

1. Faça um Fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📝 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para detalhes.

## 📧 Contato

Para dúvidas ou sugestões, entre em contato:
- Email: pretium@example.com
- GitHub Issues: [Criar uma issue](https://github.com/seu-usuario/pretium/issues)

## 🙏 Agradecimentos

- Spring Boot pela excelente framework
- Hibernate/JPA para ORM
- Comunidade Java por ferramentas e bibliotecas

---

**Desenvolvido com ❤️ para economizar seu dinheiro no supermercado!**

#   p r e t i u m  
 