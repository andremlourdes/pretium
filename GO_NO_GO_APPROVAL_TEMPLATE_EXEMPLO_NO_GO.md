# Exemplo Preenchido - Aprovacao Go/No-Go (NO-GO) - Pretium

> Documento de exemplo para treinamento de cenario de bloqueio de release e rollback.

## 1) Identificacao da release

- Sistema: `Pretium`
- Versao/Tag: `v1.3.1`
- Commit SHA: `91ab34cd56ef78ab90cd12ef34ab56cd78ef90ab`
- Data da janela: `2026-11-02`
- Horario planejado de inicio (local): `21:00 BRT`
- Horario planejado de fim (local): `22:30 BRT`
- Ambiente alvo: `producao`
- Change/Chamado: `CHG-2026-1093`

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
| Inicio da janela | 21:00 | 21:01 | Andre Silva | ☑ |
| Backup concluido | 21:10 | 21:08 | Rafael Gomes | ☑ |
| Deploy concluido | 21:35 | 21:36 | Bruno Costa | ☑ |
| Smoke test concluido | 21:55 | 21:58 | Mariana Lima | ☑ |
| Decisao Go/No-Go | 22:05 | 22:01 | Camila Rocha | ☑ |
| Rollback concluido | 22:20 | 22:17 | Bruno Costa | ☑ |
| Encerramento | 22:30 | 22:25 | Andre Silva | ☑ |

## 4) Gate tecnico (pre-go-live)

Referenciar `GO_LIVE_CHECKLIST.md` e marcar cada item critico.

| Gate | Criterio | Evidencia | Responsavel | Resultado |
|---|---|---|---|---|
| Build/Testes | Pipeline verde para a release | https://ci.exemplo.com/pretium/builds/4890 | Andre Silva | ☑ Passou |
| Segredos | `PRETIUM_JWT_SECRET` e credenciais externos | Cofre `kv-pretium-prod` (versao 19) | Juliana Nunes | ☑ Passou |
| Profiles | `SPRING_PROFILES_ACTIVE=prod,security` | Variavel em deploy job `release-prod` | Bruno Costa | ☑ Passou |
| Actuator | Apenas `health,info,prometheus` expostos | Evidencia de `curl` + politica de rede | Bruno Costa | ☑ Passou |
| Banco | Backup + conectividade + permissao minima | Relatorio DBA `backup_20261102_2107.log` | Rafael Gomes | ☑ Passou |
| Observabilidade | Prometheus scrape + alertas ativos | Dashboard `Pretium-Prod` + alerta de latencia | Bruno Costa | ☐ Falhou |
| Rollback | Plano e versao anterior prontos | Tag rollback `v1.3.0` validada | Andre Silva | ☑ Passou |

## 5) Evidencias obrigatorias

| Tipo de evidencia | Link/Arquivo | Observacao |
|---|---|---|
| Resultado de build/testes | https://ci.exemplo.com/pretium/builds/4890 | Pipeline de release aprovado |
| Logs de deploy | https://logs.exemplo.com/pretium/deploy/2026-11-02 | Sem falha de deploy |
| Saida de health/info/prometheus | `evidencias/health-info-prom-nogo.txt` | `health=UP`, porem instabilidade de latencia |
| Verificacao de endpoint protegido (401/403) | `evidencias/security-check-nogo.txt` | Comportamento correto de autorizacao |
| Screenshot dashboard e alertas | `evidencias/grafana-latency-spike.png` | p95 acima do limiar por 15 min |
| Confirmacao de backup | `evidencias/backup-ok-nogo.txt` | Backup confirmado antes da mudanca |
| Plano de rollback validado | `evidencias/rollback-executado.txt` | Rollback para `v1.3.0` realizado |

## 6) Registro de riscos e excecoes

| Risco/Excecao | Impacto | Mitigacao | Owner | Status |
|---|---|---|---|---|
| p95 acima do SLO apos deploy | Alto | Acionar rollback imediato e abrir investigacao de performance | Bruno Costa | ☑ Mitigado |
| Erro intermitente no fluxo de refresh token | Alto | Bloquear go-live e manter versao anterior | Andre Silva | ☑ Mitigado |

## 7) Decisao Go/No-Go

- Decisao final: ☐ GO  ☑ NO-GO
- Data/Hora da decisao: `2026-11-02 22:01 BRT`
- Motivo (obrigatorio em NO-GO): `Violacao de SLO de latencia (p95) e falha intermitente no fluxo de refresh token detectadas no smoke estendido.`
- Acoes imediatas: `Rollback para v1.3.0, congelar promocao da release v1.3.1 e abrir incidente INC-2026-7781 com task force tecnica.`

## 8) Assinaturas

### 8.1 Aprovacao tecnica

- Nome: Andre Silva
- Cargo/Papel: Owner Tecnico
- Assinatura (digital ou nome completo): Andre Silva
- Data/Hora: `2026-11-02 22:03 BRT`

### 8.2 Aprovacao de negocio

- Nome: Camila Rocha
- Cargo/Papel: Product Owner
- Assinatura (digital ou nome completo): Camila Rocha
- Data/Hora: `2026-11-02 22:04 BRT`

### 8.3 Aprovacao de operacao (SRE/Infra)

- Nome: Bruno Costa
- Cargo/Papel: SRE
- Assinatura (digital ou nome completo): Bruno Costa
- Data/Hora: `2026-11-02 22:04 BRT`

## 9) Pos-rollback (T+60 min)

| Verificacao | Resultado | Evidencia |
|---|---|---|
| Taxa de erro 5xx normalizada | ☑ OK | Grafana painel `API Errors` |
| Latencia retornou ao baseline | ☑ OK | Grafana painel `Latency p95` |
| Fluxo de autenticacao estavel na versao anterior | ☑ OK | smoke de login/refresh em `v1.3.0` |
| Integracoes principais estaveis | ☑ OK | logs de integracao sem erro critico |

## 10) Encaminhamentos

- Pendencias abertas para proxima iteracao: `Analisar regressao de performance e corrigir fluxo de refresh token antes de nova janela`.
- Responsavel por acompanhamento: `Andre Silva`.
- Prazo: `2026-11-07`.

