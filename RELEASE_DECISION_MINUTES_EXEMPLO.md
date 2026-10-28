# Exemplo Preenchido - Ata de Decisao de Release (Auditavel) - Pretium

> Documento de exemplo para treinamento. Substitua os dados ficticios antes de uso real.

## 1) Dados da reuniao

- Sistema: `Pretium`
- Data: `2026-10-29`
- Horario de inicio: `21:00 BRT`
- Horario de encerramento: `22:24 BRT`
- Canal/local da reuniao: `Teams - CAB Release Pretium`
- Numero da mudanca (Change): `CHG-2026-1047`
- Ambiente alvo: `producao`
- Versao/Tag avaliada: `v1.3.0`
- Commit SHA: `8f2a1c9b7d3e4a1f9a2c5d6e7f8a9b0c1d2e3f4a`

## 2) Participantes

| Nome | Papel | Time | Presenca | Assinatura |
|---|---|---|---|---|
| Andre Silva | Owner Tecnico | Backend | ☑ | Andre Silva |
| Camila Rocha | Owner de Negocio | Produto | ☑ | Camila Rocha |
| Bruno Costa | SRE/Infra | Plataforma | ☑ | Bruno Costa |
| Mariana Lima | QA | Qualidade | ☑ | Mariana Lima |
| Rafael Gomes | DBA | Dados | ☑ | Rafael Gomes |
| Juliana Nunes | Seguranca | AppSec | ☑ | Juliana Nunes |

## 3) Objetivo da sessao

Registrar deliberacao formal sobre liberacao da versao para producao, com base em evidencias tecnicas e de negocio.

## 4) Evidencias apresentadas

| Categoria | Evidencia | Link/Arquivo | Responsavel |
|---|---|---|---|
| Build e testes | Pipeline de release aprovado | https://ci.exemplo.com/pretium/builds/4821 | Andre Silva |
| Segredos e configuracao | Profiles + secret JWT + credenciais DB externos | Cofre `kv-pretium-prod`, versao 17 | Juliana Nunes |
| Banco de dados | Backup concluido + conectividade validada | `backup_20261029_2108.log` | Rafael Gomes |
| Observabilidade | Health/info/prometheus + alertas ativos | `grafana-pretium-prod.png` | Bruno Costa |
| Seguranca funcional | Endpoint protegido retorna 401 sem token | `security-check.txt` | Mariana Lima |
| Rollback | Plano validado + versao anterior pronta | Tag `v1.2.4` | Bruno Costa |

## 5) Pontos discutidos

### 5.1 Itens favoraveis ao GO

- Todos os gates criticos do checklist operacional em estado aprovado.
- Nenhuma regressao funcional no smoke de autenticacao e endpoints principais.
- Observabilidade ativa com scrape Prometheus e dashboards operacionais.

### 5.2 Riscos/ressalvas levantados

- Possivel aumento de latencia em horario de pico nas primeiras 24 horas.
- Dependencia de monitoracao ativa para reacao rapida caso p95 ultrapasse o limiar.

### 5.3 Mitigacoes acordadas

- Monitoracao assistida por 60 minutos apos deploy com war-room ativa.
- Escalonamento de replicas se p95 exceder limiar por 10 minutos consecutivos.

## 6) Deliberacao formal

- Decisao final: ☑ GO  ☐ NO-GO
- Data/Hora da decisao: `2026-10-29 22:03 BRT`
- Justificativa da decisao: `Todos os gates criticos aprovados, evidencias consistentes e plano de rollback pronto.`

### 6.1 Se GO

- Condicoes de execucao imediata:
  - Monitoracao assistida por 60 minutos.
  - Criterios de rollback ativos.
- Responsavel por acompanhar T+60: `Bruno Costa`

### 6.2 Se NO-GO

- Motivo objetivo do bloqueio: `N/A`
- Acao imediata:
  - ☐ Rollback acionado
  - ☐ Congelamento da release
  - ☐ Abertura de incidente
- Numero do incidente: `N/A`
- Proxima janela proposta: `N/A`

## 7) Plano de acao pos-decisao

| Acao | Owner | Prazo | Status |
|---|---|---|---|
| Monitorar p95 e taxa 5xx por 60 min | Bruno Costa | 2026-10-29 23:05 BRT | ☑ |
| Registrar relatorio final de janela | Andre Silva | 2026-10-30 10:00 BRT | ☑ |

## 8) Registro de conformidade e trilha de auditoria

- Checklist operacional referenciado: `GO_LIVE_CHECKLIST.md`
- Template de aprovacao referenciado: `GO_NO_GO_APPROVAL_TEMPLATE.md`
- One-page executivo referenciado: `RELEASE_ONE_PAGE_EXECUTIVE.md`
- Pacote de treinamento referenciado: `RELEASE_TRAINING_PACK.md`

## 9) Assinaturas finais

### 9.1 Aprovacao tecnica

- Nome: Andre Silva
- Cargo/Papel: Owner Tecnico
- Assinatura: Andre Silva
- Data/Hora: `2026-10-29 22:04 BRT`

### 9.2 Aprovacao de negocio

- Nome: Camila Rocha
- Cargo/Papel: Product Owner
- Assinatura: Camila Rocha
- Data/Hora: `2026-10-29 22:05 BRT`

### 9.3 Aprovacao de operacao (SRE/Infra)

- Nome: Bruno Costa
- Cargo/Papel: SRE
- Assinatura: Bruno Costa
- Data/Hora: `2026-10-29 22:05 BRT`

