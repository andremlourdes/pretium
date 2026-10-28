# Funcionalidades avançadas

## Disponível nesta entrega

- `GET /api/dashboard/summary`: resumo de produtos, lojas, cupons e gastos.
- `/`: dashboard web responsivo servido pelo Spring Boot.
- `/api/alerts`: criação, consulta e desativação de alertas de preço.
- Verificação automática de alertas a cada 5 minutos (configurável por `PRETIUM_ALERT_CHECK_INTERVAL_MS`).
- `GET /api/predictions/{productId}`: previsão baseline por média móvel dos últimos dez preços.
- `GET /api/reports/receipt/{receiptId}/pdf`: relatório PDF do cupom.
- `POST /api/ocr/receipt`: OCR de imagem multipart usando Tess4J.
- `GET /api/integrations/supermarkets/products?query=...`: adaptador para uma API externa configurada.

## Perfis opcionais

O perfil padrão não exige serviços externos.

### Redis

Ative `redis` e configure `REDIS_HOST`/`REDIS_PORT`:

```powershell
$env:SPRING_PROFILES_ACTIVE = "redis"
$env:REDIS_HOST = "localhost"
$env:REDIS_PORT = "6379"
mvn spring-boot:run
```

### Segurança

Ative `security` para HTTP Basic. O usuário é temporário em memória; defina credenciais por ambiente:

```powershell
$env:SPRING_PROFILES_ACTIVE = "security"
$env:PRETIUM_SECURITY_USER = "admin"
$env:PRETIUM_SECURITY_PASSWORD = "uma-senha-forte"
mvn spring-boot:run
```

Para produção, o próximo passo é substituir o usuário em memória por uma entidade `User`, hash persistido, refresh tokens e autorização por recurso.

### OCR

O Tess4J precisa do Tesseract e dos dados de idioma instalados no host. Configure `TESSDATA_PREFIX` apontando para a pasta que contém `por.traineddata`. O endpoint retorna o texto reconhecido; o parser automático de itens do cupom deve ser validado por supermercado antes de persistir compras.

### Supermercados

A integração é deliberadamente genérica: configure `PRETIUM_SUPERMARKET_API_URL` com uma API autorizada que aceite `GET /products?query=...` e retorne uma lista compatível com `SupermarketProductDTO`. Não foram incluídas chamadas a APIs privadas ou não documentadas de marcas.

## Fora do backend

O aplicativo mobile deve ser um cliente separado (Android/iOS/Flutter/React Native) consumindo os endpoints REST. O backend já expõe os recursos básicos necessários, mas push notifications, sincronização offline e publicação nas lojas exigem um projeto mobile e provedor de push próprios.

## Validação

```powershell
mvn clean test
```

