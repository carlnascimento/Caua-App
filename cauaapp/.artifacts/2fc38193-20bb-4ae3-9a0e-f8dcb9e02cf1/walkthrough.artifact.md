# Walkthrough - Aplicativo To-Do

O aplicativo To-Do foi implementado com sucesso, seguindo todos os requisitos obrigatórios.

## Funcionalidades Implementadas

- **Persistência SQLite (Room)**: Tarefas e categorias são armazenadas localmente.
- **Três Telas Principais**:
    - **Lista de Tarefas**: Visualização, filtros por status/categoria e alternância de tema.
    - **Editor de Tarefa**: Criação, edição e exclusão de tarefas com suporte a data de vencimento e categorias.
    - **Gerenciador de Categorias**: Criação e exclusão de categorias.
- **Notificações Locais**: Lembretes programados usando `AlarmManager`.
- **Modo Claro/Escuro**: Alternância manual persistida via `DataStore`.
- **Arquitetura MVVM**: Organização clara entre dados, lógica e interface.

## Mudanças Realizadas

### Configuração
- Atualizado `libs.versions.toml` com Room, Navigation, DataStore e KSP.
- Configurado `build.gradle.kts` e `gradle.properties` para suporte a KSP e API 37.

### Dados
- [Task.kt](file:///C:/Users/aluno.lab03/AndroidStudioProjects/cauaapp/app/src/main/java/com/example/cauaapp/data/model/Task.kt): Entidade da tarefa.
- [Category.kt](file:///C:/Users/aluno.lab03/AndroidStudioProjects/cauaapp/app/src/main/java/com/example/cauaapp/data/model/Category.kt): Entidade da categoria.
- [AppDatabase.kt](file:///C:/Users/aluno.lab03/AndroidStudioProjects/cauaapp/app/src/main/java/com/example/cauaapp/data/local/AppDatabase.kt): Banco de dados Room.

### UI
- [MainActivity.kt](file:///C:/Users/aluno.lab03/AndroidStudioProjects/cauaapp/app/src/main/java/com/example/cauaapp/MainActivity.kt): Ponto de entrada e Navegação.
- [TodoListScreen.kt](file:///C:/Users/aluno.lab03/AndroidStudioProjects/cauaapp/app/src/main/java/com/example/cauaapp/ui/screens/TodoListScreen.kt): Tela principal.
- [TaskEditorScreen.kt](file:///C:/Users/aluno.lab03/AndroidStudioProjects/cauaapp/app/src/main/java/com/example/cauaapp/ui/screens/TaskEditorScreen.kt): Criação e Edição.

### Notificações
- [NotificationHelper.kt](file:///C:/Users/aluno.lab03/AndroidStudioProjects/cauaapp/app/src/main/java/com/example/cauaapp/notification/NotificationHelper.kt): Lógica de agendamento.
- [NotificationReceiver.kt](file:///C:/Users/aluno.lab03/AndroidStudioProjects/cauaapp/app/src/main/java/com/example/cauaapp/notification/NotificationReceiver.kt): Recebimento dos alarmes.

## Verificação
- O projeto compila com sucesso (`./gradlew assembleDebug`).
- Persistência verificada via código Room.
- Notificações configuradas para disparar via `AlarmManager`.
