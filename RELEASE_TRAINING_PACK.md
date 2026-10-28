# Pacote Unificado de Treinamento de Release - Pretium

Este documento consolida os artefatos para simular e executar o ritual de aprovacao de release (GO/NO-GO).

## 1) Artefatos oficiais

- Checklist operacional: `GO_LIVE_CHECKLIST.md`
- Template formal de aprovacao: `GO_NO_GO_APPROVAL_TEMPLATE.md`
- Exemplo preenchido (GO): `GO_NO_GO_APPROVAL_TEMPLATE_EXEMPLO.md`
- Exemplo preenchido (NO-GO): `GO_NO_GO_APPROVAL_TEMPLATE_EXEMPLO_NO_GO.md`
- Ata auditavel (template): `RELEASE_DECISION_MINUTES.md`
- Ata auditavel (exemplo preenchido, GO): `RELEASE_DECISION_MINUTES_EXEMPLO.md`
- Ata auditavel (exemplo preenchido, NO-GO): `RELEASE_DECISION_MINUTES_EXEMPLO_NO_GO.md`

## 2) Ordem recomendada de uso

1. Preparar a janela com `GO_LIVE_CHECKLIST.md`.
2. Preencher `GO_NO_GO_APPROVAL_TEMPLATE.md` durante o go-live.
3. Em treinamento, usar os exemplos GO e NO-GO para simular tomada de decisao.
4. Registrar decisao final, assinaturas e evidencias.

## 3) Roteiro de facilitacao (reuniao CAB/Go-Live)

Duracao sugerida: 30 a 45 minutos.

### 3.1 Abertura (5 min)

- Confirmar objetivo da release.
- Confirmar participantes obrigatorios: tecnico, negocio, SRE/Infra, QA, DBA, seguranca.
- Confirmar janela e criterio de rollback.

### 3.2 Revisao de gates (10 a 15 min)

- Build e testes.
- Segredos e profiles (`prod,security`).
- Banco (backup e conectividade).
- Actuator e observabilidade.
- Plano de rollback.

### 3.3 Evidencias (5 a 10 min)

- Anexar links de pipeline, logs, health/info/prometheus, seguranca de endpoint protegido, dashboards e backup.

### 3.4 Decisao (5 min)

- Marcar `GO` ou `NO-GO` no template.
- Registrar motivo, horario e acoes imediatas.
- Coletar assinaturas tecnica, negocio e operacao.

### 3.5 Pos-decisao (5 a 10 min)

- Se GO: iniciar monitoracao assistida de 60 min.
- Se NO-GO: executar rollback, registrar incidente e plano de correcao.

## 4) Critérios objetivos para GO

- Todos os gates criticos marcados como `Passou`.
- Nenhuma violacao de SLO/SLA durante smoke estendido.
- Endpoints de observabilidade respondendo conforme esperado.
- Riscos residuais com mitigacao e owner definidos.

## 5) Critérios objetivos para NO-GO

- Falha em gate critico (seguranca, banco, autenticacao, observabilidade).
- Violacao de SLO (latencia/erros) sem mitigacao imediata.
- Ausencia de evidencias obrigatorias.
- Rollback indisponivel ou nao validado.

## 6) Comandos de verificacao rapida (PowerShell)

```powershell
Invoke-WebRequest -UseBasicParsing http://localhost:8080/actuator/health
Invoke-WebRequest -UseBasicParsing http://localhost:8080/actuator/info
Invoke-WebRequest -UseBasicParsing http://localhost:8080/actuator/prometheus
```

## 7) Checklist de saida da reuniao

- [ ] Template oficial preenchido (`GO_NO_GO_APPROVAL_TEMPLATE.md`).
- [ ] Evidencias anexadas e links validados.
- [ ] Decisao registrada com horario.
- [ ] Assinaturas coletadas.
- [ ] Acao pos-decisao executada (monitoracao ou rollback).

## 8) Boas praticas

- Evitar decisoes com base em percepcao: usar dados/evidencias.
- Nomear explicitamente owner e prazo para cada risco residual.
- Manter trilha de auditoria em um unico local.
- Revisar retrospectiva da janela em ate 48 horas.
