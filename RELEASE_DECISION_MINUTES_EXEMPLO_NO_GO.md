# Exemplo Preenchido - Ata de Decisao de Release (Auditavel, NO-GO) - Pretium

> Documento de exemplo para treinamento de bloqueio de release com rollback e registro formal.

## 1) Dados da reuniao

- Sistema: `Pretium`
- Data: `2026-11-02`
- Horario de inicio: `21:00 BRT`
- Horario de encerramento: `22:27 BRT`
- Canal/local da reuniao: `Teams - CAB Release Pretium`
- Numero da mudanca (Change): `CHG-2026-1093`
- Ambiente alvo: `producao`
- Versao/Tag avaliada: `v1.3.1`
- Commit SHA: `91ab34cd56ef78ab90cd12ef34ab56cd78ef90ab`

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
| Build e testes | Pipeline de release aprovado | https://ci.exemplo.com/pretium/builds/4890 | Andre Silva |
| Segredos e configuracao | Profiles + secret JWT + credenciais DB externos | Cofre `kv-pretium-prod`, versao 19 | Juliana Nunes |
| Banco de dados | Backup concluido + conectividade validada | `backup_20261102_2107.log` | Rafael Gomes |
| Observabilidade | Alerta de p95 acima do limiar apos deploy | `grafana-latency-spike.png` | Bruno Costa |
| Seguranca funcional | Endpoint protegido retorna 401 sem token | `security-check-nogo.txt` | Mariana Lima |
| Rollback | Plano validado e executado para `v1.3.0` | `rollback-executado.txt` | Bruno Costa |

## 5) Pontos discutidos

### 5.1 Itens favoraveis ao GO

- Build e testes de release aprovados.
- Configuracao de seguranca e secrets em conformidade.
- Backup de banco concluido antes da mudanca.

### 5.2 Riscos/ressalvas levantados

- Latencia p95 acima do SLO por periodo sustentado.
- Falha intermitente no fluxo de refresh token durante smoke estendido.

### 5.3 Mitigacoes acordadas

- Bloqueio imediato da promocao para producao.
- Rollback para versao estavel anterior.
- Abertura de incidente com task force tecnica.

## 6) Deliberacao formal

- Decisao final: ☐ GO  ☑ NO-GO
- Data/Hora da decisao: `2026-11-02 22:01 BRT`
- Justificativa da decisao: `Violacao de SLO de latencia e instabilidade no fluxo de refresh token durante validacao pos-deploy.`

### 6.1 Se GO

- Condicoes de execucao imediata:
  - Monitoracao assistida por 60 minutos.
  - Criterios de rollback ativos.
- Responsavel por acompanhar T+60: `N/A`

### 6.2 Se NO-GO

- Motivo objetivo do bloqueio: `p95 acima do limite acordado e regressao funcional intermitente em autenticacao.`
- Acao imediata:
  - ☑ Rollback acionado
  - ☑ Congelamento da release
  - ☑ Abertura de incidente
- Numero do incidente: `INC-2026-7781`
- Proxima janela proposta: `2026-11-07 21:00 BRT`

## 7) Plano de acao pos-decisao

| Acao | Owner | Prazo | Status |
|---|---|---|---|
| Concluir RCA da regressao de performance | Bruno Costa | 2026-11-04 | ☑ |
| Corrigir fluxo de refresh token e validar em homologacao | Andre Silva | 2026-11-05 | ☐ |
| Reexecutar bateria de smoke estendido | Mariana Lima | 2026-11-06 | ☐ |

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
- Data/Hora: `2026-11-02 22:03 BRT`

### 9.2 Aprovacao de negocio

- Nome: Camila Rocha
- Cargo/Papel: Product Owner
- Assinatura: Camila Rocha
- Data/Hora: `2026-11-02 22:04 BRT`

### 9.3 Aprovacao de operacao (SRE/Infra)

- Nome: Bruno Costa
- Cargo/Papel: SRE
- Assinatura: Bruno Costa
- Data/Hora: `2026-11-02 22:04 BRT`

