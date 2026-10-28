# Template de Aprovacao Go/No-Go - Pretium

Use este documento na janela de release para registrar decisao formal de entrada em producao.

## 1) Identificacao da release

- Sistema: `Pretium`
- Versao/Tag:
- Commit SHA:
- Data da janela:
- Horario planejado de inicio (local):
- Horario planejado de fim (local):
- Ambiente alvo:
- Change/Chamado:

## 2) Responsaveis (RACI simplificado)

| Papel | Nome | Time | Contato | Responsabilidade |
|---|---|---|---|---|
| Owner Tecnico |  |  |  | Coordenar execucao tecnica |
| Owner de Negocio |  |  |  | Autorizar Go/No-Go pelo negocio |
| SRE/Infra |  |  |  | Deploy, observabilidade, rollback |
| DBA |  |  |  | Backup, validacao de banco |
| Segurança |  |  |  | Validacao de risco e conformidade |
| QA |  |  |  | Evidencias de teste e smoke |

## 3) Janela de execucao e marcos

| Marco | Previsto | Realizado | Responsavel | Status |
|---|---|---|---|---|
| Inicio da janela |  |  |  | ☐ |
| Backup concluido |  |  |  | ☐ |
| Deploy concluido |  |  |  | ☐ |
| Smoke test concluido |  |  |  | ☐ |
| Decisao Go/No-Go |  |  |  | ☐ |
| Encerramento |  |  |  | ☐ |

## 4) Gate tecnico (pre-go-live)

Referenciar `GO_LIVE_CHECKLIST.md` e marcar cada item critico.

| Gate | Criterio | Evidencia | Responsavel | Resultado |
|---|---|---|---|---|
| Build/Testes | Pipeline verde para a release | Link pipeline: |  | ☐ Passou / ☐ Falhou |
| Segredos | `PRETIUM_JWT_SECRET` e credenciais externos | Print/config vault: |  | ☐ Passou / ☐ Falhou |
| Profiles | `SPRING_PROFILES_ACTIVE=prod,security` | Env do runtime: |  | ☐ Passou / ☐ Falhou |
| Actuator | Apenas `health,info,prometheus` expostos | `curl`/request: |  | ☐ Passou / ☐ Falhou |
| Banco | Backup + conectividade + permissao minima | Log/relatorio DBA: |  | ☐ Passou / ☐ Falhou |
| Observabilidade | Prometheus scrape + alertas ativos | Screenshot Grafana/Prom: |  | ☐ Passou / ☐ Falhou |
| Rollback | Plano e versao anterior prontos | Procedimento/link: |  | ☐ Passou / ☐ Falhou |

## 5) Evidencias obrigatorias

Anexe links/arquivos de evidencias abaixo.

| Tipo de evidencia | Link/Arquivo | Observacao |
|---|---|---|
| Resultado de build/testes |  |  |
| Logs de deploy |  |  |
| Saida de health/info/prometheus |  |  |
| Verificacao de endpoint protegido (401/403) |  |  |
| Screenshot dashboard e alertas |  |  |
| Confirmacao de backup |  |  |
| Plano de rollback validado |  |  |

## 6) Registro de riscos e excecoes

| Risco/Excecao | Impacto | Mitigacao | Owner | Status |
|---|---|---|---|---|
|  |  |  |  | ☐ Aberto / ☐ Mitigado |

## 7) Decisao Go/No-Go

- Decisao final: ☐ GO  ☐ NO-GO
- Data/Hora da decisao:
- Motivo (obrigatorio em NO-GO):
- Acoes imediatas:

## 8) Assinaturas

### 8.1 Aprovacao tecnica

- Nome:
- Cargo/Papel:
- Assinatura (digital ou nome completo):
- Data/Hora:

### 8.2 Aprovacao de negocio

- Nome:
- Cargo/Papel:
- Assinatura (digital ou nome completo):
- Data/Hora:

### 8.3 Aprovacao de operacao (SRE/Infra)

- Nome:
- Cargo/Papel:
- Assinatura (digital ou nome completo):
- Data/Hora:

## 9) Pos-go-live (T+60 min)

| Verificacao | Resultado | Evidencia |
|---|---|---|
| Taxa de erro 5xx dentro do esperado | ☐ OK / ☐ NOK |  |
| Latencia dentro do SLO | ☐ OK / ☐ NOK |  |
| Fluxo de autenticacao sem regressao | ☐ OK / ☐ NOK |  |
| Integracoes principais estaveis | ☐ OK / ☐ NOK |  |

## 10) Encaminhamentos

- Pendencias abertas para proxima iteracao:
- Responsavel por acompanhamento:
- Prazo:

