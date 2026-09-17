package com.example.cauaapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.cauaapp.data.model.Task
import com.example.cauaapp.ui.viewmodel.TodoViewModel
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    viewModel: TodoViewModel,
    onNavigateToTaskEditor: (Long?) -> Unit,
    onNavigateToCategoryManager: () -> Unit,
    onToggleTheme: () -> Unit,
    isDarkMode: Boolean
) {
    val tasks by viewModel.filteredTasks.collectAsState(initial = emptyList())
    val categories by viewModel.allCategories.collectAsState(initial = emptyList())
    
    var showStatusFilter by remember { mutableStateOf(false) }
    var showCategoryFilter by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Minhas Tarefas") },
                actions = {
                    IconButton(onClick = onToggleTheme) {
                        Icon(
                            imageVector = if (isDarkMode) Icons.Default.LightMode else Icons.Default.DarkMode,
                            contentDescription = "Alternar Tema"
                        )
                    }
                    IconButton(onClick = onNavigateToCategoryManager) {
                        Icon(Icons.Default.Category, contentDescription = "Categorias")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onNavigateToTaskEditor(null) }) {
                Icon(Icons.Default.Add, contentDescription = "Nova Tarefa")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            Row(modifier = Modifier.fillMaxWidth().padding(8.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(onClick = { showStatusFilter = true }) { Text("Status") }
                Button(onClick = { showCategoryFilter = true }) { Text("Categoria") }
            }

            LazyColumn {
                items(tasks) { task ->
                    TaskItem(
                        task = task,
                        onToggleCompletion = { viewModel.toggleTaskCompletion(task) },
                        onClick = { onNavigateToTaskEditor(task.id) }
                    )
                }
            }
        }

        if (showStatusFilter) {
            AlertDialog(
                onDismissRequest = { showStatusFilter = false },
                title = { Text("Filtrar por Status") },
                text = {
                    Column {
                        TextButton(onClick = { viewModel.setStatusFilter(null); showStatusFilter = false }) { Text("Todas") }
                        TextButton(onClick = { viewModel.setStatusFilter(false); showStatusFilter = false }) { Text("Pendentes") }
                        TextButton(onClick = { viewModel.setStatusFilter(true); showStatusFilter = false }) { Text("Concluídas") }
                    }
                },
                confirmButton = {}
            )
        }

        if (showCategoryFilter) {
            AlertDialog(
                onDismissRequest = { showCategoryFilter = false },
                title = { Text("Filtrar por Categoria") },
                text = {
                    Column {
                        TextButton(onClick = { viewModel.setCategoryFilter(null); showCategoryFilter = false }) { Text("Todas") }
                        categories.forEach { category ->
                            TextButton(onClick = { viewModel.setCategoryFilter(category.id); showCategoryFilter = false }) {
                                Text(category.name)
                            }
                        }
                    }
                },
                confirmButton = {}
            )
        }
    }
}

@Composable
fun TaskItem(task: Task, onToggleCompletion: () -> Unit, onClick: () -> Unit) {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
    
    ListItem(
        modifier = Modifier.fillMaxWidth(),
        headlineContent = {
            Text(
                text = task.title,
                textDecoration = if (task.completed) TextDecoration.LineThrough else null
            )
        },
        supportingContent = {
            Column {
                if (task.description.isNotBlank()) {
                    Text(task.description)
                }
                task.dueDateTime?.let {
                    Text("Vence em: ${it.format(formatter)}")
                }
            }
        },
        leadingContent = {
            Checkbox(checked = task.completed, onCheckedChange = { onToggleCompletion() })
        },
        trailingContent = {
            Button(onClick = onClick) { Text("Editar") }
        }
    )
}
