package com.example.todolist.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.todolist.TaskItem
import com.example.todolist.room.TaskDatabase
import java.util.*

class TaskRepository(context: Context) {
    private val db = TaskDatabase.getInstance(context)
    private val taskItemDao = db.taskDao()

    val allTasks: LiveData<List<TaskItem>> = taskItemDao.getAllTasks()

    suspend fun insert(task: TaskItem) {
        taskItemDao.insert(task)
    }

    suspend fun update(task: TaskItem) {
        taskItemDao.update(task)
    }

    suspend fun delete(task: TaskItem) {
        taskItemDao.delete(task)
    }

    suspend fun deleteAll() {
        taskItemDao.deleteAll()
    }

    fun getTaskById(taskId: UUID): LiveData<TaskItem?> {
        return taskItemDao.getTaskById(taskId)
    }

}
