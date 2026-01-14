---
agent: 'agent'
description: 'Create a new implementation plan file for new features, refactoring existing code or upgrading packages, design, architecture or infrastructure.'
tools: ['changes', 'search/codebase', 'edit/editFiles', 'extensions', 'fetch', 'githubRepo', 'openSimpleBrowser', 'problems', 'runTasks', 'search', 'search/searchResults', 'runCommands/terminalLastCommand', 'runCommands/terminalSelection', 'testFailure', 'usages', 'vscodeAPI']
---
# Create Implementation Plan

## Primary Directive

Your goal is to create a new implementation plan file for `${input:PlanPurpose}`. Your output must be machine-readable, deterministic, and structured for autonomous execution by other AI systems or humans.
Use "Galego" languaje to redact the plan

## Execution Context

This prompt is designed for AI-to-AI communication and automated processing. All instructions must be interpreted literally and executed systematically without human interpretation or clarification.

## Core Requirements

- Generate implementation plans that are fully executable by AI agents or humans
- Use deterministic language with zero ambiguity
- Structure all content for automated parsing and execution
- Ensure complete self-containment with no external dependencies for understanding

## Plan Structure Requirements

Plans must consist of discrete, atomic phases containing executable tasks. Each phase must be independently processable by AI agents or humans without cross-phase dependencies unless explicitly declared.

## Phase Architecture

- Each phase must have measurable completion criteria
- Tasks within phases must be executable in parallel unless dependencies are specified
- All task descriptions must include specific file paths, function names, and exact implementation details
- No task should require human interpretation or decision-making

## AI-Optimized Implementation Standards

- Use explicit, unambiguous language with zero interpretation required
- Structure all content as machine-parseable formats (tables, lists, structured data)
- Include specific file paths, line numbers, and exact code references where applicable
- Define all variables, constants, and configuration values explicitly
- Provide complete context within each task description
- Use standardized prefixes for all identifiers (REQ-, TASK-, etc.)
- Include validation criteria that can be automatically verified

## Output File Specifications

- Save implementation plan files in `/plan/` directory
- Use naming convention: `[purpose]-[component]-[version].md`
- Purpose prefixes: `upgrade|refactor|feature|data|infrastructure|process|architecture|design`
- Example: `upgrade-system-command-4.md`, `feature-auth-module-1.md`
- File must be valid Markdown with proper front matter structure

## Mandatory Template Structure

All implementation plans must strictly adhere to the following template. Each section is required and must be populated with specific, actionable content. AI agents must validate template compliance before execution.

## Template Validation Rules

- All front matter fields must be present and properly formatted
- All section headers must match exactly (case-sensitive)
- All identifier prefixes must follow the specified format
- Tables must include all required columns
- No placeholder text may remain in the final output

## Status

The status of the implementation plan must be clearly defined in the front matter and must reflect the current state of the plan. The status can be one of the following (status_color in brackets): `Completed` (bright green badge), `In progress` (yellow badge), `Planned` (blue badge), `Deprecated` (red badge), or `On Hold` (orange badge). It should also be displayed as a badge in the introduction section.

```md
---
goal: [Título Conciso Que Describa el Objetivo del Plan de Implementación del Paquete]
version: [Opcional: ej., 1.0, Fecha]
date_created: [YYYY-MM-DD]
last_updated: [Opcional: YYYY-MM-DD]
owner: [Opcional: Equipo/Individuo responsable de esta especificación]
status: 'Completado'|'En progreso'|'Planificado'|'Deprecado'|'En espera'
tags: [Opcional: Lista de etiquetas o categorías relevantes, ej., `feature`, `upgrade`, `chore`, `architecture`, `migration`, `bug` etc]
---

# Introducción

![Estado: <status>](https://img.shields.io/badge/status-<status>-<status_color>)

[Una breve introducción concisa al plan y el objetivo que pretende lograr.]

## 1. Requisitos y Restricciones

[Enumera explícitamente todos los requisitos y restricciones que afectan el plan y cómo se implementa. Usa viñetas o tablas para mayor claridad.]

- **REQ-001**: Requisito 1
- **SEC-001**: Requisito de Seguridad 1
- **[3 LETRAS]-001**: Otro Requisito 1
- **CON-001**: Restricción 1
- **GUD-001**: Pauta 1
- **PAT-001**: Patrón a seguir 1

## 2. Pasos de Implementación

### Fase de Implementación 1

- GOAL-001: [Describe el objetivo de esta fase, ej., "Implementar característica X", "Refactorizar módulo Y", etc.]

| Tarea | Descripción | Completado | Fecha |
|-------|-------------|-----------|-------|
| TASK-001 | Descripción de la tarea 1 | ✅ | 2025-04-25 |
| TASK-002 | Descripción de la tarea 2 | |  |
| TASK-003 | Descripción de la tarea 3 | |  |

### Fase de Implementación 2

- GOAL-002: [Describe el objetivo de esta fase, ej., "Implementar característica X", "Refactorizar módulo Y", etc.]

| Tarea | Descripción | Completado | Fecha |
|-------|-------------|-----------|-------|
| TASK-004 | Descripción de la tarea 4 | |  |
| TASK-005 | Descripción de la tarea 5 | |  |
| TASK-006 | Descripción de la tarea 6 | |  |

## 3. Alternativas

[Una lista de viñetas de cualquier enfoque alternativo que se consideró y por qué no fue elegido. Esto ayuda a proporcionar contexto y justificación del enfoque elegido.]

- **ALT-001**: Enfoque alternativo 1
- **ALT-002**: Enfoque alternativo 2

## 4. Dependencias

[Enumera cualquier dependencia que deba abordarse, como librerías, frameworks u otros componentes en los que se basa el plan.]

- **DEP-001**: Dependencia 1
- **DEP-002**: Dependencia 2

## 5. Archivos

[Enumera los archivos que se verán afectados por la característica o tarea de refactorización.]

- **FILE-001**: Descripción del archivo 1
- **FILE-002**: Descripción del archivo 2

## 6. Pruebas

[Enumera las pruebas que deben implementarse para verificar la característica o tarea de refactorización.]

- **TEST-001**: Descripción de la prueba 1
- **TEST-002**: Descripción de la prueba 2

## 7. Riesgos y Suposiciones

[Enumera cualquier riesgo o suposición relacionada con la implementación del plan.]

- **RISK-001**: Riesgo 1
- **ASSUMPTION-001**: Suposición 1

## 8. Especificaciones Relacionadas / Lectura Adicional

[Enlace a especificación relacionada 1]
[Enlace a documentación externa relevante]
```