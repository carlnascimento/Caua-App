package com.example.cauaapp.data.local

import androidx.room.*
import com.example.cauaapp.data.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks ORDER BY createdAt DESC")
    fun getAllTasks(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE id = :id")
    suspend fun getTaskById(id: Long): Task?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task): Long

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)
    
    @Query("SELECT * FROM tasks WHERE completed = :completed")
    fun getTasksByStatus(completed: Boolean): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE categoryId = :categoryId")
    fun getTasksByCategory(categoryId: Long): Flow<List<Task>>

    @Query("UPDATE tasks SET categoryId = NULL WHERE categoryId = :categoryId")
    suspend fun clearCategoryFromTasks(categoryId: Long)
}
