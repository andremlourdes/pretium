# Exemplo Preenchido - Aprovacao Go/No-Go - Pretium

> Documento de exemplo para treinamento do ritual de release. Ajuste os dados antes de uso real.

## 1) Identificacao da release

- Sistema: `Pretium`
- Versao/Tag: `v1.3.0`
- Commit SHA: `8f2a1c9b7d3e4a1f9a2c5d6e7f8a9b0c1d2e3f4a`
- Data da janela: `2026-10-29`
- Horario planejado de inicio (local): `21:00 BRT`
- Horario planejado de fim (local): `22:30 BRT`
- Ambiente alvo: `producao`
- Change/Chamado: `CHG-2026-1047`

## 2) Responsaveis (RACI simplificado)

| Papel | Nome | Time | Contato | Responsabilidade |
|---|---|---|---|---|
| Owner Tecnico | Andre Silva | Backend | andre.silva@empresa.com | Coordenar execucao tecnica |
| Owner de Negocio | Camila Rocha | Produto | camila.rocha@empresa.com | Autorizar Go/No-Go pelo negocio |
| SRE/Infra | Bruno Costa | Plataforma | bruno.costa@empresa.com | Deploy, observabilidade, rollback |
| DBA | Rafael Gomes | Dados | rafael.gomes@empresa.com | Backup, validacao de banco |
| Segurança | Juliana Nunes | AppSec | juliana.nunes@empresa.com | Validacao de risco e conformidade |
| QA | Mariana Lima | Qualidade | mariana.lima@empresa.com | Evidencias de teste e smoke |

## 3) Janela de execucao e marcos

| Marco | Previsto | Realizado | Responsavel | Status |
|---|---|---|---|---|
| Inicio da janela | 21:00 | 21:02 | Andre Silva | ☑ |
| Backup concluido | 21:10 | 21:09 | Rafael Gomes | ☑ |
| Deploy concluido | 21:35 | 21:33 | Bruno Costa | ☑ |
| Smoke test concluido | 21:55 | 21:52 | Mariana Lima | ☑ |
| Decisao Go/No-Go | 22:05 | 22:03 | Camila Rocha | ☑ |
| Encerramento | 22:30 | 22:22 | Andre Silva | ☑ |

## 4) Gate tecnico (pre-go-live)

Referenciar `GO_LIVE_CHECKLIST.md` e marcar cada item critico.

| Gate | Criterio | Evidencia | Responsavel | Resultado |
|---|---|---|---|---|
| Build/Testes | Pipeline verde para a release | https://ci.exemplo.com/pretium/builds/4821 | Andre Silva | ☑ Passou |
| Segredos | `PRETIUM_JWT_SECRET` e credenciais externos | Cofre `kv-pretium-prod` (versao 17) | Juliana Nunes | ☑ Passou |
| Profiles | `SPRING_PROFILES_ACTIVE=prod,security` | Variavel em deploy job `release-prod` | Bruno Costa | ☑ Passou |
| Actuator | Apenas `health,info,prometheus` expostos | Evidencia de `curl` + captura de firewall | Bruno Costa | ☑ Passou |
| Banco | Backup + conectividade + permissao minima | Relatorio DBA `backup_20261029_2108.log` | Rafael Gomes | ☑ Passou |
| Observabilidade | Prometheus scrape + alertas ativos | Dashboard `Pretium-Prod` + alerta ativo | Bruno Costa | ☑ Passou |
| Rollback | Plano e versao anterior prontos | Tag rollback `v1.2.4` validada | Andre Silva | ☑ Passou |

## 5) Evidencias obrigatorias

| Tipo de evidencia | Link/Arquivo | Observacao |
|---|---|---|
| Resultado de build/testes | https://ci.exemplo.com/pretium/builds/4821 | 100% dos testes do pipeline de release passaram |
| Logs de deploy | https://logs.exemplo.com/pretium/deploy/2026-10-29 | Sem erro critico |
| Saida de health/info/prometheus | `evidencias/health-info-prom.txt` | HTTP 200 em todos |
| Verificacao de endpoint protegido (401/403) | `evidencias/security-check.txt` | Sem token retornou 401 |
| Screenshot dashboard e alertas | `evidencias/grafana-pretium-prod.png` | Latencia normal |
| Confirmacao de backup | `evidencias/backup-ok.txt` | Snapshot finalizado antes do deploy |
| Plano de rollback validado | `evidencias/rollback-plan.md` | Teste de comando ok |

## 6) Registro de riscos e excecoes

| Risco/Excecao | Impacto | Mitigacao | Owner | Status |
|---|---|---|---|---|
| Aumento de latencia em horario de pico | Medio | Monitorar 60 min e escalar replicas se p95 > limiar | Bruno Costa | ☑ Mitigado |

## 7) Decisao Go/No-Go

- Decisao final: ☑ GO  ☐ NO-GO
- Data/Hora da decisao: `2026-10-29 22:03 BRT`
- Motivo (obrigatorio em NO-GO): `N/A`
- Acoes imediatas: `Iniciar monitoracao assistida de 60 min e comunicar status no canal #release-pretium`.

## 8) Assinaturas

### 8.1 Aprovacao tecnica

- Nome: Andre Silva
- Cargo/Papel: Owner Tecnico
- Assinatura (digital ou nome completo): Andre Silva
- Data/Hora: `2026-10-29 22:04 BRT`

### 8.2 Aprovacao de negocio

- Nome: Camila Rocha
- Cargo/Papel: Product Owner
- Assinatura (digital ou nome completo): Camila Rocha
- Data/Hora: `2026-10-29 22:05 BRT`

### 8.3 Aprovacao de operacao (SRE/Infra)

- Nome: Bruno Costa
- Cargo/Papel: SRE
- Assinatura (digital ou nome completo): Bruno Costa
- Data/Hora: `2026-10-29 22:05 BRT`

## 9) Pos-go-live (T+60 min)

| Verificacao | Resultado | Evidencia |
|---|---|---|
| Taxa de erro 5xx dentro do esperado | ☑ OK | Grafana painel `API Errors` |
| Latencia dentro do SLO | ☑ OK | Grafana painel `Latency p95` |
| Fluxo de autenticacao sem regressao | ☑ OK | teste smoke de login/refresh |
| Integracoes principais estaveis | ☑ OK | logs de integracao sem erro critico |

## 10) Encaminhamentos

- Pendencias abertas para proxima iteracao: `Revisar tuning de pool de conexoes para pico de sexta-feira`.
- Responsavel por acompanhamento: `Andre Silva`.
- Prazo: `2026-11-05`.

