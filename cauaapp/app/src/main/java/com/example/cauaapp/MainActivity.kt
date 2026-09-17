package com.example.cauaapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cauaapp.ui.screens.CategoryManagerScreen
import com.example.cauaapp.ui.screens.TaskEditorScreen
import com.example.cauaapp.ui.screens.TodoListScreen
import com.example.cauaapp.ui.theme.CauaappTheme
import com.example.cauaapp.ui.viewmodel.ThemeViewModel
import com.example.cauaapp.ui.viewmodel.ThemeViewModelFactory
import com.example.cauaapp.ui.viewmodel.TodoViewModel
import com.example.cauaapp.ui.viewmodel.TodoViewModelFactory

class MainActivity : ComponentActivity() {
    
    private val todoViewModel: TodoViewModel by viewModels {
        val app = application as TodoApplication
        TodoViewModelFactory(app.repository, app.notificationHelper)
    }

    private val themeViewModel: ThemeViewModel by viewModels {
        ThemeViewModelFactory((application as TodoApplication).themePreferences)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isDarkMode by themeViewModel.isDarkMode.collectAsState()
            val navController = rememberNavController()
            val context = LocalContext.current

            val permissionLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission()
            ) { isGranted ->
                // Handle permission result if needed
            }

            LaunchedEffect(Unit) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    if (ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.POST_NOTIFICATIONS
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                }
            }

            CauaappTheme(darkTheme = isDarkMode) {
                NavHost(navController = navController, startDestination = "todoList") {
                    composable("todoList") {
                        TodoListScreen(
                            viewModel = todoViewModel,
                            onNavigateToTaskEditor = { taskId ->
                                navController.navigate("taskEditor?taskId=$taskId")
                            },
                            onNavigateToCategoryManager = {
                                navController.navigate("categoryManager")
                            },
                            onToggleTheme = { themeViewModel.toggleTheme() },
                            isDarkMode = isDarkMode
                        )
                    }
                    composable("taskEditor?taskId={taskId}") { backStackEntry ->
                        val taskId = backStackEntry.arguments?.getString("taskId")?.toLongOrNull()
                        TaskEditorScreen(
                            viewModel = todoViewModel,
                            taskId = taskId,
                            onNavigateBack = { navController.popBackStack() }
                        )
                    }
                    composable("categoryManager") {
                        CategoryManagerScreen(
                            viewModel = todoViewModel,
                            onNavigateBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}
