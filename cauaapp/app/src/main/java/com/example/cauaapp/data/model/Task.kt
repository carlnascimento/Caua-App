package com.example.cauaapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String = "",
    val completed: Boolean = false,
    val dueDateTime: LocalDateTime? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val categoryId: Long? = null
)
