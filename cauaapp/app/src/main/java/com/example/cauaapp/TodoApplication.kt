package com.example.cauaapp

import android.app.Application
import androidx.room.Room
import com.example.cauaapp.data.local.AppDatabase
import com.example.cauaapp.data.local.ThemePreferences
import com.example.cauaapp.data.repository.TodoRepository

class TodoApplication : Application() {
    val database: AppDatabase by lazy {
        Room.databaseBuilder(this, AppDatabase::class.java, "todo_database").build()
    }
    val repository: TodoRepository by lazy {
        TodoRepository(database.taskDao(), database.categoryDao())
    }
    val themePreferences: ThemePreferences by lazy {
        ThemePreferences(this)
    }
    val notificationHelper: com.example.cauaapp.notification.NotificationHelper by lazy {
        com.example.cauaapp.notification.NotificationHelper(this)
    }
}
