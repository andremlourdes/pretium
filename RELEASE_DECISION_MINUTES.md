# Ata de Decisao de Release (Auditavel) - Pretium

## 1) Dados da reuniao

- Sistema: `Pretium`
- Data:
- Horario de inicio:
- Horario de encerramento:
- Canal/local da reuniao:
- Numero da mudanca (Change):
- Ambiente alvo: `producao`
- Versao/Tag avaliada:
- Commit SHA:

## 2) Participantes

| Nome | Papel | Time | Presenca | Assinatura |
|---|---|---|---|---|
|  | Owner Tecnico |  | ☐ |  |
|  | Owner de Negocio |  | ☐ |  |
|  | SRE/Infra |  | ☐ |  |
|  | QA |  | ☐ |  |
|  | DBA |  | ☐ |  |
|  | Seguranca |  | ☐ |  |

## 3) Objetivo da sessao

Registrar deliberacao formal sobre liberacao da versao para producao, com base em evidencias tecnicas e de negocio.

## 4) Evidencias apresentadas

| Categoria | Evidencia | Link/Arquivo | Responsavel |
|---|---|---|---|
| Build e testes | Pipeline de release |  |  |
| Segredos e configuracao | Profiles + secret JWT + credenciais DB |  |  |
| Banco de dados | Backup, conectividade, privilegios |  |  |
| Observabilidade | Health/info/prometheus + alertas |  |  |
| Seguranca funcional | Endpoint protegido (401/403) |  |  |
| Rollback | Plano validado + versao anterior pronta |  |  |

## 5) Pontos discutidos

### 5.1 Itens favoraveis ao GO

- 
- 

### 5.2 Riscos/ressalvas levantados

- 
- 

### 5.3 Mitigacoes acordadas

- 
- 

## 6) Deliberacao formal

- Decisao final: ☐ GO  ☐ NO-GO
- Data/Hora da decisao:
- Justificativa da decisao:

### 6.1 Se GO

- Condicoes de execucao imediata:
  - Monitoracao assistida por 60 minutos.
  - Criterios de rollback ativos.
- Responsavel por acompanhar T+60:

### 6.2 Se NO-GO

- Motivo objetivo do bloqueio:
- Acao imediata:
  - ☐ Rollback acionado
  - ☐ Congelamento da release
  - ☐ Abertura de incidente
- Numero do incidente:
- Proxima janela proposta:

## 7) Plano de acao pos-decisao

| Acao | Owner | Prazo | Status |
|---|---|---|---|
|  |  |  | ☐ |
|  |  |  | ☐ |

## 8) Registro de conformidade e trilha de auditoria

- Checklist operacional referenciado: `GO_LIVE_CHECKLIST.md`
- Template de aprovacao referenciado: `GO_NO_GO_APPROVAL_TEMPLATE.md`
- One-page executivo referenciado: `RELEASE_ONE_PAGE_EXECUTIVE.md`
- Pacote de treinamento referenciado: `RELEASE_TRAINING_PACK.md`

## 9) Assinaturas finais

### 9.1 Aprovacao tecnica

- Nome:
- Cargo/Papel:
- Assinatura:
- Data/Hora:

### 9.2 Aprovacao de negocio

- Nome:
- Cargo/Papel:
- Assinatura:
- Data/Hora:

### 9.3 Aprovacao de operacao (SRE/Infra)

- Nome:
- Cargo/Papel:
- Assinatura:
- Data/Hora:

