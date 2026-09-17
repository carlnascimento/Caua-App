package com.example.cauaapp.ui.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.cauaapp.data.model.Category
import com.example.cauaapp.data.model.Task
import com.example.cauaapp.ui.viewmodel.TodoViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskEditorScreen(
    viewModel: TodoViewModel,
    taskId: Long?,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val categories by viewModel.allCategories.collectAsState(initial = emptyList())
    
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var completed by remember { mutableStateOf(false) }
    var dueDateTime by remember { mutableStateOf<LocalDateTime?>(null) }
    var selectedCategoryId by remember { mutableStateOf<Long?>(null) }
    
    val existingTask = remember(taskId) {
        if (taskId != null) {
            // In a real app, we'd fetch this from the ViewModel/Repository
            // For this UI, we'll collect all tasks and find the one.
            null
        } else null
    }

    val tasks by viewModel.allTasks.collectAsState(initial = emptyList())
    LaunchedEffect(taskId, tasks) {
        if (taskId != null) {
            val task = tasks.find { it.id == taskId }
            if (task != null) {
                title = task.title
                description = task.description
                completed = task.completed
                dueDateTime = task.dueDateTime
                selectedCategoryId = task.categoryId
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (taskId == null) "Nova Tarefa" else "Editar Tarefa") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                actions = {
                    if (taskId != null) {
                        IconButton(onClick = {
                            val taskToDelete = tasks.find { it.id == taskId }
                            if (taskToDelete != null) {
                                viewModel.deleteTask(taskToDelete)
                                onNavigateBack()
                            }
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "Excluir")
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título *") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descrição") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = dueDateTime?.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) ?: "Sem data de vencimento",
                    modifier = Modifier.padding(top = 12.dp)
                )
                Button(onClick = {
                    val calendar = Calendar.getInstance()
                    DatePickerDialog(context, { _, year, month, day ->
                        TimePickerDialog(context, { _, hour, minute ->
                            dueDateTime = LocalDateTime.of(year, month + 1, day, hour, minute)
                        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
                }) {
                    Text("Definir Data")
                }
                if (dueDateTime != null) {
                    TextButton(onClick = { dueDateTime = null }) { Text("Limpar") }
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            var expanded by remember { mutableStateOf(false) }
            Box {
                OutlinedButton(onClick = { expanded = true }) {
                    Text(categories.find { it.id == selectedCategoryId }?.name ?: "Selecionar Categoria")
                }
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    DropdownMenuItem(
                        text = { Text("Nenhuma") },
                        onClick = { selectedCategoryId = null; expanded = false }
                    )
                    categories.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category.name) },
                            onClick = { selectedCategoryId = category.id; expanded = false }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        val task = Task(
                            id = taskId ?: 0,
                            title = title,
                            description = description,
                            completed = completed,
                            dueDateTime = dueDateTime,
                            categoryId = selectedCategoryId
                        )
                        if (taskId == null) {
                            viewModel.insertTask(task)
                        } else {
                            viewModel.updateTask(task)
                        }
                        onNavigateBack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Salvar")
            }
        }
    }
}
