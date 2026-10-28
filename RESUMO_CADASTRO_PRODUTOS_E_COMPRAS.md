# Resumo do cadastro de produtos e compras — Pretium

## 1. Cadastro de produtos

A entidade `Product` possui os seguintes campos:

| Campo | Descrição | Disponível no frontend |
|---|---|---:|
| `id` | Identificador do produto | Automático |
| `name` | Nome do produto | Sim |
| `barcode` | Código de barras | Sim |
| `category` | Categoria | Sim |
| `brand` | Marca | Sim |
| `unit` | Unidade de medida, como KG, L ou UN | Sim |
| `weight` | Peso do produto | Sim |
| `description` | Descrição adicional | Sim |
| `status` | Estado do produto: ACTIVE, INACTIVE ou DISCONTINUED | Backend |
| `createdAt` | Data de criação | Automática |
| `updatedAt` | Data da última atualização | Automática |

A tela correspondente é:

```text
pretium-frontend/src/components/ProductDialog.tsx
```

A API utilizada é:

```http
POST /api/products
GET /api/products
GET /api/products/{id}
PUT /api/products/{id}
DELETE /api/products/{id}
```

## 2. Preço do produto

O preço não é armazenado diretamente em `Product`. Isso permite que o mesmo produto tenha preços diferentes por loja e por data.

### 2.1 Preço em uma compra

A entidade `PurchaseItem` registra o preço do produto comprado:

| Campo | Descrição |
|---|---|
| `productId` | Produto comprado |
| `quantity` | Quantidade comprada |
| `unitPrice` | Preço unitário |
| `totalPrice` | Preço total do item |
| `discountAmount` | Desconto aplicado |
| `taxAmount` | Imposto aplicado |

A tela correspondente é:

```text
pretium-frontend/src/pages/ReceiptsPage.tsx
```

O endpoint utilizado para adicionar um item é:

```http
POST /api/receipts/{receiptId}/items?productId={productId}&quantity={quantity}&unitPrice={unitPrice}
```

### 2.2 Histórico de preços

A entidade `PriceHistory` permite acompanhar a variação do preço:

| Campo | Descrição |
|---|---|
| `product` | Produto relacionado |
| `store` | Loja relacionada |
| `price` | Preço normal |
| `discountedPrice` | Preço promocional |
| `priceType` | REGULAR, PROMOTION ou SALE |
| `promotionDescription` | Descrição da promoção |
| `recordedDate` | Data em que o preço foi registrado |
| `createdAt` | Data de criação do registro |

Endpoints de análise:

```http
GET /api/prices/analysis/{productId}
GET /api/prices/history/{productId}?startDate={startDate}&endDate={endDate}
GET /api/prices/compare/city/{productId}/{city}
GET /api/prices/compare/state/{productId}/{state}
GET /api/prices/compare/country/{productId}/{country}
```

## 3. Data da compra

A data da compra pertence à entidade `Receipt`, por meio do campo:

```text
purchaseDate
```

O frontend possui esse campo na tela:

```text
pretium-frontend/src/pages/ReceiptsPage.tsx
```

O formato utilizado pela API é ISO 8601:

```text
aaaa-MM-ddTHH:mm:ss
```

Exemplo:

```text
2026-10-27T14:30:00
```

## 4. Cadastro do cupom de compra

A entidade `Receipt` possui:

| Campo | Descrição |
|---|---|
| `storeId` | Loja onde a compra foi realizada |
| `receiptNumber` | Número único do cupom |
| `purchaseDate` | Data e hora da compra |
| `totalAmount` | Valor total da compra |
| `discountAmount` | Desconto total |
| `taxAmount` | Impostos |
| `paymentMethod` | Forma de pagamento |
| `notes` | Observações |
| `status` | ACTIVE, CANCELLED ou ARCHIVED |
| `items` | Lista de produtos comprados |

Endpoints principais:

```http
POST /api/receipts
GET /api/receipts
GET /api/receipts/{id}
GET /api/receipts/store/{storeId}
GET /api/receipts/date-range?startDate={startDate}&endDate={endDate}
PUT /api/receipts/{receiptId}
PUT /api/receipts/{receiptId}/cancel
```

Para criar um cupom, a API exige:

```text
storeId
receiptNumber
purchaseDate
totalAmount
```

## 5. Relacionamento dos dados

```text
Product
  └── PurchaseItem
        ├── quantity
        ├── unitPrice
        └── totalPrice

Receipt
  ├── Store
  ├── purchaseDate
  ├── totalAmount
  └── PurchaseItem

Product
  └── PriceHistory
        ├── Store
        ├── price
        ├── discountedPrice
        └── recordedDate
```

## 6. Relatórios

O backend gera relatório PDF de um cupom por meio do endpoint:

```http
GET /api/reports/receipt/{receiptId}/pdf
```

Na tela de cupons existe um botão `PDF` para baixar o relatório.

O relatório pode conter:

- número do cupom;
- loja;
- data da compra;
- itens comprados;
- quantidade;
- preço unitário;
- preço total;
- total da compra.

## 7. Conclusão

O projeto possui suporte para:

- cadastro de produtos;
- nome, marca, categoria e código de barras;
- peso e unidade de medida;
- registro de preço unitário;
- registro de preço total;
- quantidade comprada;
- data da compra;
- loja da compra;
- descontos e impostos;
- histórico de preços;
- comparação de preços entre lojas;
- previsão de preços;
- alertas de preço;
- geração de relatórios PDF.

O preço não fica diretamente no cadastro básico do produto. Ele é registrado no item comprado (`PurchaseItem`) ou no histórico de preços (`PriceHistory`), o que permite controlar variações por loja e por data.
A única observação importante é que o preço não é cadastrado diretamente no formulário de produto. Ele é cadastrado ao adicionar o produto a um cupom de compra, por meio do campo unitPrice. Essa modelagem é adequada, pois permite que o mesmo produto tenha preços diferentes em lojas e datas diferentes.
