package com.example.cauaapp.ui.viewmodel

import androidx.lifecycle.*
import com.example.cauaapp.data.model.Category
import com.example.cauaapp.data.model.Task
import com.example.cauaapp.data.repository.TodoRepository
import com.example.cauaapp.notification.NotificationHelper
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TodoViewModel(
    private val repository: TodoRepository,
    private val notificationHelper: NotificationHelper
) : ViewModel() {

    val allTasks = repository.allTasks
    val allCategories = repository.allCategories

    private val _statusFilter = MutableStateFlow<Boolean?>(null) // null = All, false = Pending, true = Completed
    private val _categoryFilter = MutableStateFlow<Long?>(null)

    val filteredTasks: Flow<List<Task>> = combine(allTasks, _statusFilter, _categoryFilter) { tasks, status, categoryId ->
        tasks.filter { task ->
            (status == null || task.completed == status) &&
            (categoryId == null || task.categoryId == categoryId)
        }
    }

    fun setStatusFilter(completed: Boolean?) {
        _statusFilter.value = completed
    }

    fun setCategoryFilter(categoryId: Long?) {
        _categoryFilter.value = categoryId
    }

    fun insertTask(task: Task) = viewModelScope.launch {
        val id = repository.insertTask(task)
        if (task.dueDateTime != null) {
            notificationHelper.scheduleNotification(task.copy(id = id))
        }
    }

    fun updateTask(task: Task) = viewModelScope.launch {
        repository.updateTask(task)
        notificationHelper.cancelNotification(task.id)
        if (task.dueDateTime != null && !task.completed) {
            notificationHelper.scheduleNotification(task)
        }
    }

    fun toggleTaskCompletion(task: Task) = viewModelScope.launch {
        val updatedTask = task.copy(completed = !task.completed)
        repository.updateTask(updatedTask)
        if (updatedTask.completed) {
            notificationHelper.cancelNotification(updatedTask.id)
        } else if (updatedTask.dueDateTime != null) {
            notificationHelper.scheduleNotification(updatedTask)
        }
    }

    fun deleteTask(task: Task) = viewModelScope.launch {
        repository.deleteTask(task)
        notificationHelper.cancelNotification(task.id)
    }

    fun insertCategory(category: Category) = viewModelScope.launch {
        repository.insertCategory(category)
    }

    fun deleteCategory(category: Category) = viewModelScope.launch {
        repository.deleteCategory(category)
    }
}

class TodoViewModelFactory(
    private val repository: TodoRepository,
    private val notificationHelper: NotificationHelper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TodoViewModel(repository, notificationHelper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
