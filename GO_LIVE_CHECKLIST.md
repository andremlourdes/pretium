# Go-Live Checklist - Pretium

Este checklist cobre os itens finais para colocar o Pretium em producao com seguranca, observabilidade e plano de rollback.

Para aprovacao formal da release, use tambem `GO_NO_GO_APPROVAL_TEMPLATE.md`.

## 0) Gate de release (obrigatorio)

- [ ] Build e testes automatizados passaram na branch de release.
- [ ] Artefato versionado pronto (`Pretium-<versao>.jar` ou imagem Docker tagueada).
- [ ] Mudancas revisadas e aprovadas (PR + changelog).
- [ ] Janela de deploy e responsaveis definidos (owner tecnico e owner de negocio).

## 1) Perfis e configuracao de runtime

- [ ] Runtime com profiles explicitos: `prod,security`.
- [ ] `PRETIUM_JWT_SECRET` definido por secret manager/variavel de ambiente (sem fallback em prod).
- [ ] Credenciais de banco em variaveis de ambiente (sem senha hardcoded).
- [ ] `PORT` validada para o ambiente de execucao.

Variaveis minimas:

- [ ] `SPRING_PROFILES_ACTIVE=prod,security`
- [ ] `PRETIUM_DB_URL`
- [ ] `PRETIUM_DB_USERNAME`
- [ ] `PRETIUM_DB_PASSWORD`
- [ ] `PRETIUM_JWT_SECRET` (minimo 32 bytes)

## 2) Banco de dados e dados criticos

- [ ] Banco PostgreSQL acessivel a partir da aplicacao.
- [ ] Backup/snapshot executado antes do deploy.
- [ ] Usuario de banco com privilegios minimos necessarios.
- [ ] Politica de retencao de backup validada.

## 3) Seguranca

- [ ] Endpoints de autenticacao e autorizacao testados em ambiente alvo.
- [ ] Headers de seguranca ativos (CSP e XSS) no profile `security`.
- [ ] Superficie de actuator limitada para producao.
- [ ] Secrets fora de codigo, fora de `README` e fora de logs.

## 4) Actuator e observabilidade

Exposicao esperada em producao:

- [ ] `/actuator/health`
- [ ] `/actuator/info`
- [ ] `/actuator/prometheus`

Validacoes:

- [ ] Prometheus conseguindo coletar `/actuator/prometheus`.
- [ ] Dashboards de Grafana apontando para o Prometheus correto.
- [ ] Alertas minimos ativos (app down, alta latencia, erro 5xx).

## 5) Deploy (runbook)

### 5.1 Maven (host sem container)

```powershell
cd "C:\it\Pretium\Pretium"
$env:SPRING_PROFILES_ACTIVE = "prod,security"
$env:PRETIUM_DB_URL = "jdbc:postgresql://<host>:5432/pretium"
$env:PRETIUM_DB_USERNAME = "<usuario>"
$env:PRETIUM_DB_PASSWORD = "<senha>"
$env:PRETIUM_JWT_SECRET = "<segredo-forte-32-bytes-ou-mais>"
mvn clean package
java -jar .\target\Pretium-1.0-SNAPSHOT.jar
```

### 5.2 Docker Compose

```powershell
cd "C:\it\Pretium\Pretium"
Copy-Item .env.production.example .env
# editar .env e preencher segredos/senhas fortes

docker compose --env-file .env up -d --build
docker compose ps
```

## 6) Smoke test pos-deploy

- [ ] Aplicacao responde HTTP 200 no endpoint de health.
- [ ] Endpoint de info responde.
- [ ] Endpoint prometheus responde para scrape.
- [ ] Endpoint protegido retorna 401/403 sem token valido.
- [ ] Fluxo de login e refresh token funcionando.

Comandos rapidos (PowerShell):

```powershell
Invoke-WebRequest -UseBasicParsing http://localhost:8080/actuator/health
Invoke-WebRequest -UseBasicParsing http://localhost:8080/actuator/info
Invoke-WebRequest -UseBasicParsing http://localhost:8080/actuator/prometheus
```

## 7) Plano de rollback

- [ ] Versao anterior pronta para redeploy imediato.
- [ ] Procedimento de rollback testado (blue/green ou tag anterior).
- [ ] Criterio de rollback definido (erros 5xx, latencia, falha de login, etc.).
- [ ] Canal de comunicacao de incidente definido.

Rollback (exemplo com compose):

```powershell
# trocar a tag/imagem para a versao anterior e reaplicar
cd "C:\it\Pretium\Pretium"
docker compose --env-file .env up -d
```

## 8) Encerramento de go-live

- [ ] Monitorar 30-60 min apos deploy.
- [ ] Registrar evidencias (health, logs, metricas, timestamp do deploy).
- [ ] Confirmacao final de negocio.
- [ ] Abrir tarefa de hardening/remediacoes pendentes detectadas na janela.
