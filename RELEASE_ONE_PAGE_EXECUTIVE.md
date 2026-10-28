# One-Page Executivo - Go/No-Go de Release (Pretium)

## Objetivo

Apoiar a decisao executiva de liberar ou bloquear a entrada em producao com base em evidencias objetivas.

## Escopo da release

- Sistema: `Pretium`
- Versao/Tag:
- Data da janela:
- Ambiente: `producao`
- Dono tecnico:
- Dono de negocio:

## Status rapido (preencher na reuniao)

| Area | Status | Evidencia resumida | Owner |
|---|---|---|---|
| Build e testes | ☐ Verde / ☐ Amarelo / ☐ Vermelho |  |  |
| Seguranca e secrets | ☐ Verde / ☐ Amarelo / ☐ Vermelho |  |  |
| Banco e backup | ☐ Verde / ☐ Amarelo / ☐ Vermelho |  |  |
| Observabilidade e alertas | ☐ Verde / ☐ Amarelo / ☐ Vermelho |  |  |
| Rollback pronto | ☐ Verde / ☐ Amarelo / ☐ Vermelho |  |  |

## Criterios minimos para GO

- Todos os gates criticos em verde.
- `SPRING_PROFILES_ACTIVE=prod,security` confirmado no runtime.
- `PRETIUM_JWT_SECRET` externo e sem fallback em producao.
- Endpoints actuator esperados respondendo (`health`, `info`, `prometheus`).
- Plano de rollback validado e versao anterior disponivel.

## Criterios automáticos para NO-GO

- Falha em seguranca, autenticacao, backup/banco ou rollback.
- Violacao de SLO (latencia/erro) sem mitigacao imediata.
- Evidencias obrigatorias ausentes/inconsistentes.

## Principais riscos e mitigacoes

| Risco | Impacto | Mitigacao | Owner | Status |
|---|---|---|---|---|
|  |  |  |  |  |

## Decisao executiva

- Decisao: ☐ GO  ☐ NO-GO
- Data/Hora:
- Justificativa curta:
- Acao imediata:
  - Se GO: monitoracao assistida por 60 minutos.
  - Se NO-GO: rollback imediato e abertura de incidente.

## Assinaturas

- Aprovacao tecnica (nome e hora):
- Aprovacao negocio (nome e hora):
- Aprovacao operacao/SRE (nome e hora):

## Referencias

- Checklist detalhado: `GO_LIVE_CHECKLIST.md`
- Template formal: `GO_NO_GO_APPROVAL_TEMPLATE.md`
- Exemplo GO: `GO_NO_GO_APPROVAL_TEMPLATE_EXEMPLO.md`
- Exemplo NO-GO: `GO_NO_GO_APPROVAL_TEMPLATE_EXEMPLO_NO_GO.md`
- Pacote unificado de treinamento: `RELEASE_TRAINING_PACK.md`

