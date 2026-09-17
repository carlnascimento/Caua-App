# Plano de Implementação - Aplicativo To-Do

Este plano descreve a estratégia para implementar um aplicativo de lista de tarefas completo com persistência SQLite, categorias, filtragem e notificações locais.

## User Review Required

> [!IMPORTANT]
> A exclusão de uma categoria resultará na remoção da referência da categoria em todas as tarefas associadas (as tarefas permanecerão, mas sem categoria).
> As notificações serão disparadas usando `AlarmManager` para garantir precisão no horário de vencimento.
> O modo claro/escuro será controlado por um botão na barra superior e o estado será persistido usando `DataStore`.

## Open Questions
- Existe alguma preferência por ícones específicos para as categorias ou podemos usar um conjunto padrão do Material Design? (Assumirei padrão por enquanto).

## Proposed Changes

### Dependências e Configuração
Adição de bibliotecas necessárias para Room, Navigation, DataStore (preferências de tema) e ícones estendidos.

#### [MODIFY] [libs.versions.toml](file:///C:/Users/aluno.lab03/AndroidStudioProjects/cauaapp/gradle/libs.versions.toml)
#### [MODIFY] [build.gradle.kts (app)](file:///C:/Users/aluno.lab03/AndroidStudioProjects/cauaapp/app/build.gradle.kts)

### Modelo de Dados e Persistência (Room)
Definição das entidades `Task` e `Category`, e configuração do banco de dados Room.

#### [NEW] [Task.kt](file:///C:/Users/aluno.lab03/AndroidStudioProjects/cauaapp/app/src/main/java/com/example/cauaapp/data/model/Task.kt)
#### [NEW] [Category.kt](file:///C:/Users/aluno.lab03/AndroidStudioProjects/cauaapp/app/src/main/java/com/example/cauaapp/data/model/Category.kt)
#### [NEW] [AppDatabase.kt](file:///C:/Users/aluno.lab03/AndroidStudioProjects/cauaapp/app/src/main/java/com/example/cauaapp/data/local/AppDatabase.kt)
#### [NEW] [TaskDao.kt](file:///C:/Users/aluno.lab03/AndroidStudioProjects/cauaapp/app/src/main/java/com/example/cauaapp/data/local/TaskDao.kt)
#### [NEW] [CategoryDao.kt](file:///C:/Users/aluno.lab03/AndroidStudioProjects/cauaapp/app/src/main/java/com/example/cauaapp/data/local/CategoryDao.kt)
#### [NEW] [TodoRepository.kt](file:///C:/Users/aluno.lab03/AndroidStudioProjects/cauaapp/app/src/main/java/com/example/cauaapp/data/repository/TodoRepository.kt)

### Camada de UI e Navegação
Implementação das três telas obrigatórias usando Jetpack Compose e Navigation Compose. Suporte a temas claro e escuro.

#### [MODIFY] [MainActivity.kt](file:///C:/Users/aluno.lab03/AndroidStudioProjects/cauaapp/app/src/main/java/com/example/cauaapp/MainActivity.kt)
#### [NEW] [Theme.kt](file:///C:/Users/aluno.lab03/AndroidStudioProjects/cauaapp/app/src/main/java/com/example/cauaapp/ui/theme/Theme.kt)
#### [NEW] [ThemeViewModel.kt](file:///C:/Users/aluno.lab03/AndroidStudioProjects/cauaapp/app/src/main/java/com/example/cauaapp/ui/viewmodel/ThemeViewModel.kt)
#### [NEW] [TodoListScreen.kt](file:///C:/Users/aluno.lab03/AndroidStudioProjects/cauaapp/app/src/main/java/com/example/cauaapp/ui/screens/TodoListScreen.kt)
#### [NEW] [TaskEditorScreen.kt](file:///C:/Users/aluno.lab03/AndroidStudioProjects/cauaapp/app/src/main/java/com/example/cauaapp/ui/screens/TaskEditorScreen.kt)
#### [NEW] [CategoryManagerScreen.kt](file:///C:/Users/aluno.lab03/AndroidStudioProjects/cauaapp/app/src/main/java/com/example/cauaapp/ui/screens/CategoryManagerScreen.kt)
#### [NEW] [TodoViewModel.kt](file:///C:/Users/aluno.lab03/AndroidStudioProjects/cauaapp/app/src/main/java/com/example/cauaapp/ui/viewmodel/TodoViewModel.kt)

### Notificações
Implementação do `BroadcastReceiver` e lógica para agendar/cancelar notificações usando `AlarmManager`.

#### [NEW] [NotificationReceiver.kt](file:///C:/Users/aluno.lab03/AndroidStudioProjects/cauaapp/app/src/main/java/com/example/cauaapp/notification/NotificationReceiver.kt)
#### [NEW] [NotificationHelper.kt](file:///C:/Users/aluno.lab03/AndroidStudioProjects/cauaapp/app/src/main/java/com/example/cauaapp/notification/NotificationHelper.kt)

## Verification Plan

### Automated Tests
- Build do projeto: `./gradlew assembleDebug`
- Testes unitários para o DAO do Room (opcional, se houver tempo).

### Manual Verification
- Criar tarefa com data futura e verificar se a notificação aparece.
- Editar tarefa e verificar se a notificação é atualizada/cancelada.
- Excluir tarefa e verificar se a notificação é cancelada.
- Testar filtros de status e categoria.
- Verificar persistência fechando e abrindo o app.
