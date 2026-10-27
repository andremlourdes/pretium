# 📚 Guia Completo de Uso - Pretium API

## 🔧 Configuração Inicial

### 1. Criar uma Localização

```bash
curl -X POST http://localhost:8081/api/locations \
  -H "Content-Type: application/json" \
  -d '{
    "country": "Brasil",
    "state": "São Paulo",
    "city": "São Paulo",
    "zipCode": "01310100",
    "latitude": -23.5505,
    "longitude": -46.6333
  }'
```

**Resposta:**
```json
{
  "id": 1,
  "country": "Brasil",
  "state": "São Paulo",
  "city": "São Paulo",
  "zipCode": "01310100",
  "latitude": -23.5505,
  "longitude": -46.6333
}
```

### 2. Criar um Supermercado

```bash
curl -X POST http://localhost:8081/api/stores/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Supermercado Central",
    "description": "O melhor supermercado da região",
    "phone": "(11) 3000-1000",
    "website": "www.central.com.br"
  }'
```

**Resposta:**
```json
{
  "id": 1,
  "name": "Supermercado Central",
  "description": "O melhor supermercado da região",
  "phone": "(11) 3000-1000",
  "website": "www.central.com.br",
  "location": {
    "id": 1,
    "country": "Brasil",
    "state": "São Paulo",
    "city": "São Paulo",
    "zipCode": "01310100",
    "latitude": -23.5505,
    "longitude": -46.6333
  },
  "status": "ACTIVE",
  "createdAt": "2024-10-26T10:30:00",
  "updatedAt": "2024-10-26T10:30:00"
}
```

### 3. Criar Produtos

```bash
# Produto 1: Arroz
curl -X POST http://localhost:8081/api/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Arroz Integral 5kg",
    "barcode": "7898123456789",
    "category": "Alimentos Secos",
    "brand": "Tio João",
    "unit": "KG",
    "weight": 5.0,
    "description": "Arroz integral de qualidade premium"
  }'

# Produto 2: Feijão
curl -X POST http://localhost:8081/api/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Feijão Preto 1kg",
    "barcode": "7898234567890",
    "category": "Alimentos Secos",
    "brand": "Vale Verde",
    "unit": "KG",
    "weight": 1.0,
    "description": "Feijão preto tipo 1"
  }'

# Produto 3: Óleo
curl -X POST http://localhost:8081/api/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Óleo de Soja 900ml",
    "barcode": "7898345678901",
    "category": "Óleos e Temperos",
    "brand": "Soya",
    "unit": "L",
    "weight": 0.9,
    "description": "Óleo de soja refinado"
  }'
```

---

## 🛒 Gerenciamento de Cupons e Compras

### 4. Registrar um Cupom de Compra

```bash
curl -X POST "http://localhost:8081/api/receipts?storeId=1&receiptNumber=001234567&purchaseDate=2024-10-26T10:30:00&totalAmount=150.50" \
  -H "Content-Type: application/json"
```

**Resposta:**
```json
{
  "id": 1,
  "storeId": 1,
  "storeName": "Supermercado Central",
  "receiptNumber": "001234567",
  "purchaseDate": "2024-10-26T10:30:00",
  "totalAmount": 150.50,
  "discountAmount": 0,
  "taxAmount": 0,
  "paymentMethod": null,
  "notes": null,
  "status": "ACTIVE",
  "items": [],
  "createdAt": "2024-10-26T10:30:00",
  "updatedAt": "2024-10-26T10:30:00"
}
```

### 5. Adicionar Itens ao Cupom

```bash
# Item 1: Arroz
curl -X POST "http://localhost:8081/api/receipts/1/items?productId=1&quantity=1&unitPrice=28.99"

# Item 2: Feijão
curl -X POST "http://localhost:8081/api/receipts/1/items?productId=2&quantity=2&unitPrice=7.49"

# Item 3: Óleo
curl -X POST "http://localhost:8081/api/receipts/1/items?productId=3&quantity=1&unitPrice=5.99"
```

### 6. Listar Itens de um Cupom

```bash
curl -X GET http://localhost:8081/api/receipts/1/items
```

**Resposta:**
```json
[
  {
    "id": 1,
    "receiptId": 1,
    "productId": 1,
    "productName": "Arroz Integral 5kg",
    "quantity": 1,
    "unitPrice": 28.99,
    "totalPrice": 28.99,
    "discountAmount": 0,
    "taxAmount": 0,
    "itemNumber": null
  },
  {
    "id": 2,
    "receiptId": 1,
    "productId": 2,
    "productName": "Feijão Preto 1kg",
    "quantity": 2,
    "unitPrice": 7.49,
    "totalPrice": 14.98,
    "discountAmount": 0,
    "taxAmount": 0,
    "itemNumber": null
  },
  {
    "id": 3,
    "receiptId": 1,
    "productId": 3,
    "productName": "Óleo de Soja 900ml",
    "quantity": 1,
    "unitPrice": 5.99,
    "totalPrice": 5.99,
    "discountAmount": 0,
    "taxAmount": 0,
    "itemNumber": null
  }
]
```

---

## 💰 Análise e Comparação de Preços

### 7. Análise Completa de Preços de um Produto

```bash
curl -X GET http://localhost:8081/api/prices/analysis/1
```

**Resposta:**
```json
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
      "productId": 1,
      "productName": "Arroz Integral 5kg",
      "storeId": 2,
      "storeName": "Mercado Central",
      "city": "São Paulo",
      "state": "São Paulo",
      "country": "Brasil",
      "price": 26.50,
      "discountedPrice": null,
      "priceType": "REGULAR",
      "recordedDate": "2024-10-26T10:30:00"
    },
    {
      "productId": 1,
      "productName": "Arroz Integral 5kg",
      "storeId": 1,
      "storeName": "Supermercado Central",
      "city": "São Paulo",
      "state": "São Paulo",
      "country": "Brasil",
      "price": 28.99,
      "discountedPrice": null,
      "priceType": "REGULAR",
      "recordedDate": "2024-10-26T10:30:00"
    },
    {
      "productId": 1,
      "productName": "Arroz Integral 5kg",
      "storeId": 3,
      "storeName": "Supermercado Carioca",
      "city": "Rio de Janeiro",
      "state": "Rio de Janeiro",
      "country": "Brasil",
      "price": 29.90,
      "discountedPrice": null,
      "priceType": "REGULAR",
      "recordedDate": "2024-10-26T10:30:00"
    }
  ]
}
```

### 8. Comparar Preços por Cidade

```bash
curl -X GET "http://localhost:8081/api/prices/compare/city/1/São Paulo"
```

**Resposta:**
```json
[
  {
    "productId": 1,
    "productName": "Arroz Integral 5kg",
    "storeId": 2,
    "storeName": "Mercado Central",
    "city": "São Paulo",
    "state": "São Paulo",
    "country": "Brasil",
    "price": 26.50,
    "discountedPrice": null,
    "priceType": "REGULAR",
    "recordedDate": "2024-10-26T10:30:00"
  },
  {
    "productId": 1,
    "productName": "Arroz Integral 5kg",
    "storeId": 1,
    "storeName": "Supermercado Central",
    "city": "São Paulo",
    "state": "São Paulo",
    "country": "Brasil",
    "price": 28.99,
    "discountedPrice": null,
    "priceType": "REGULAR",
    "recordedDate": "2024-10-26T10:30:00"
  }
]
```

**Insight:** O "Mercado Central" oferece o melhor preço de R$ 26.50 em São Paulo!

### 9. Comparar Preços por Estado

```bash
curl -X GET "http://localhost:8081/api/prices/compare/state/1/São Paulo"
```

### 10. Comparar Preços por País

```bash
curl -X GET "http://localhost:8081/api/prices/compare/country/1/Brasil"
```

### 11. Histórico de Preços (Série Temporal)

```bash
curl -X GET "http://localhost:8081/api/prices/history/1?startDate=2024-01-01T00:00:00&endDate=2024-12-31T23:59:59"
```

---

## 📊 Exemplos Práticos de Economia

### Exemplo 1: Economizar no Arroz

**Objetivo:** Encontrar o melhor preço para Arroz Integral 5kg

```bash
# Análise de preços
curl -X GET http://localhost:8081/api/prices/analysis/1
```

**Resultado:**
- Preço mínimo: **R$ 26.50** (Mercado Central)
- Preço máximo: **R$ 29.90** (Supermercado Carioca)
- Economia: **R$ 3.40 por saco** (11.4% de desconto!)

**Recomendação:** Compre no "Mercado Central" para economizar!

### Exemplo 2: Comparar Preços em Sua Cidade

```bash
curl -X GET "http://localhost:8081/api/prices/compare/city/2/Rio de Janeiro"
```

**Resultado:** Veja todos os preços do Feijão Preto em lojas do Rio de Janeiro e escolha a melhor!

### Exemplo 3: Encontrar as Melhores Lojas

```bash
# Listar todas as lojas da sua cidade
curl -X GET "http://localhost:8081/api/stores/city/São Paulo"
```

**Resultado:** Identifique quais supermercados têm os menores preços!

---

## 🔍 Consultas Úteis

### Listar Todos os Produtos
```bash
curl -X GET http://localhost:8081/api/products
```

### Buscar Produto por Código de Barras
```bash
curl -X GET "http://localhost:8081/api/products/barcode/7898123456789"
```

### Listar Produtos por Categoria
```bash
curl -X GET "http://localhost:8081/api/products/category/Alimentos Secos"
```

### Listar Produtos por Marca
```bash
curl -X GET "http://localhost:8081/api/products/brand/Tio João"
```

### Listar Cupons de um Período
```bash
curl -X GET "http://localhost:8081/api/receipts/date-range?startDate=2024-01-01T00:00:00&endDate=2024-12-31T23:59:59"
```

### Listar Supermercados de um Estado
```bash
curl -X GET "http://localhost:8081/api/stores/state/São Paulo"
```

---

## 💡 Dicas de Uso

1. **Registre todos os seus cupons**: Quanto mais dados, melhor as análises!
2. **Compare preços regularmente**: Acompanhe as variações de preço ao longo do tempo
3. **Use filtros por cidade**: Identifique as melhores lojas da sua região
4. **Monitore suas categorias favoritas**: Veja quais marcas oferecem melhor custo-benefício
5. **Compartilhe dados**: Quanto mais pessoas contribuirem, mais precisa será o banco de dados

---

## 🚨 Tratamento de Erros

### Erros Comuns

| Status | Erro | Solução |
|--------|------|---------|
| 404 | Produto não encontrado | Verifique o ID do produto |
| 404 | Cupom não encontrado | Registre um novo cupom |
| 400 | Dados inválidos | Verifique o formato dos parâmetros |
| 500 | Erro interno do servidor | Tente novamente mais tarde |

---

## 📈 Próximas Análises Planejadas

- [ ] Gráfico de evolução de preços ao longo do tempo
- [ ] Relatório de economia mensal
- [ ] Alertas de queda de preço
- [ ] Previsão de preços (Machine Learning)
- [ ] Dashboard web interativo
- [ ] Comparação de cestas de compra entre lojas
- [ ] Análise de promoções por período
- [ ] Recomendações personalizadas de compra

---

Desenvolvido com ❤️ para ajudar você a economizar!
