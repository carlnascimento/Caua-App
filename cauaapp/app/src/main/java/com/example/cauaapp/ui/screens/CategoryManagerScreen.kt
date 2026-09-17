package com.example.cauaapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cauaapp.data.model.Category
import com.example.cauaapp.ui.viewmodel.TodoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryManagerScreen(
    viewModel: TodoViewModel,
    onNavigateBack: () -> Unit
) {
    val categories by viewModel.allCategories.collectAsState(initial = emptyList())
    var newCategoryName by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gerenciar Categorias") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = newCategoryName,
                    onValueChange = { newCategoryName = it },
                    label = { Text("Nova Categoria") },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    if (newCategoryName.isNotBlank()) {
                        viewModel.insertCategory(Category(name = newCategoryName))
                        newCategoryName = ""
                    }
                }) {
                    Text("Add")
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(categories) { category ->
                    ListItem(
                        headlineContent = { Text(category.name) },
                        trailingContent = {
                            IconButton(onClick = { viewModel.deleteCategory(category) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Excluir")
                            }
                        }
                    )
                }
            }
        }
    }
}
