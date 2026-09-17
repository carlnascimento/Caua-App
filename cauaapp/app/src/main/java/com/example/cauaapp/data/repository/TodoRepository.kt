package com.example.cauaapp.data.repository

import com.example.cauaapp.data.local.CategoryDao
import com.example.cauaapp.data.local.TaskDao
import com.example.cauaapp.data.model.Category
import com.example.cauaapp.data.model.Task
import kotlinx.coroutines.flow.Flow

class TodoRepository(
    private val taskDao: TaskDao,
    private val categoryDao: CategoryDao
) {
    val allTasks: Flow<List<Task>> = taskDao.getAllTasks()
    val allCategories: Flow<List<Category>> = categoryDao.getAllCategories()

    suspend fun getTaskById(id: Long): Task? = taskDao.getTaskById(id)

    suspend fun insertTask(task: Task): Long = taskDao.insertTask(task)

    suspend fun updateTask(task: Task) = taskDao.updateTask(task)

    suspend fun deleteTask(task: Task) = taskDao.deleteTask(task)

    fun getTasksByStatus(completed: Boolean): Flow<List<Task>> = taskDao.getTasksByStatus(completed)

    fun getTasksByCategory(categoryId: Long): Flow<List<Task>> = taskDao.getTasksByCategory(categoryId)

    suspend fun insertCategory(category: Category) = categoryDao.insertCategory(category)

    suspend fun updateCategory(category: Category) = categoryDao.updateCategory(category)

    suspend fun deleteCategory(category: Category) {
        // Implementation decision: when a category is deleted, set task's categoryId to null
        // This is handled by a custom query in TaskDao or by Room if set up with foreign keys.
        // For simplicity, we'll manually update tasks here or just let the tasks stay with an invalid ID.
        // Better: Update tasks where categoryId = category.id to null.
        // I should add this method to TaskDao.
        taskDao.clearCategoryFromTasks(category.id)
        categoryDao.deleteCategory(category)
    }
}
