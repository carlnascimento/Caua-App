# BUILD_LOG.md

## Entry 1: Project Initialization
**Prompt / Solicitação**: Especificação do aplicativo To-Do para o agente de programação.
**Resumo da Decisão**: Início do projeto. Decidido utilizar Kotlin com Jetpack Compose para a UI, Room para persistência SQLite, e MVVM como arquitetura.
**Ações Realizadas**: Criação do arquivo `BUILD_LOG.md`.
**Resultado**: Projeto iniciado.
**Problemas / Erros**: Nenhum.
**Correções Tentadas**: N/A.
**Status Atual**: Concluído (Setup inicial).

## Entry 3: Execution Started
**Prompt / Solicitação**: "aprovados pode prosseguir"
**Resumo da Decisão**: Início da execução das tarefas planejadas. Criado `task.artifact.md` para rastreamento.
**Ações Realizadas**: Criação do `task.artifact.md`.
**Resultado**: Próximo passo é a configuração de dependências.
**Problemas / Erros**: Nenhum.
**Correções Tentadas**: N/A.
**Status Atual**: Em progresso.

## Entry 4: Implementation of Core Features
**Prompt / Solicitação**: Implementação completa conforme especificado.
**Resumo da Decisão**: Implementado Room para persistência, DataStore para tema, e AlarmManager para notificações. Decidido usar `LocalDateTime` para datas. Aumentado `minSdk` para 26.
**Ações Realizadas**: Criação de entidades, DAOs, Database, Repository, ViewModels, Screens e Notification system.
**Resultado**: Aplicativo funcional com todas as telas e persistência.
**Problemas / Erros**: Erro de versão do plugin KSP corrigido para `2.2.10-2.0.2`. Avisos de API level resolvidos aumentando `minSdk`.
**Correções Tentadas**: Atualização do `libs.versions.toml` com a versão correta do KSP após pesquisa.
**Status Atual**: Concluído.

## Entry 5: Final Review
**Resumo Final**:
- **Arquitetura**: MVVM com Repository Pattern.
- **Dependências**: Room, Navigation Compose, DataStore Preferences, Material Icons Extended, KSP.
- **SQLite**: Utilizado Room para abstração. Estratégia de exclusão de categoria: tarefas perdem o `categoryId` (setado para NULL).
- **Gerenciamento de Estado**: StateFlow no ViewModel, coletado como State na UI Compose.
- **Navegação**: Navigation Compose com passagem de argumentos via URL (`taskId`).
- **Notificações**: AlarmManager para disparos precisos. Solicitação de permissão `POST_NOTIFICATIONS` no Android 13+.
- **Limitações**: O agendamento exato (`SCHEDULE_EXACT_ALARM`) pode exigir que o usuário habilite manualmente em certas versões do Android, tratado com `canScheduleExactAlarms()`.
- **Bugs restantes**: Nenhum identificado durante o build e revisão estática.
