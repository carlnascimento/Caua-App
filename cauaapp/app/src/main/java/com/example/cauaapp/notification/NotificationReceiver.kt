package com.example.cauaapp.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val taskId = intent.getLongExtra("TASK_ID", -1L)
        val taskTitle = intent.getStringExtra("TASK_TITLE") ?: "Tarefa pendente"

        if (taskId != -1L) {
            val notificationHelper = NotificationHelper(context)
            notificationHelper.showNotification(taskId, taskTitle)
        }
    }
}
