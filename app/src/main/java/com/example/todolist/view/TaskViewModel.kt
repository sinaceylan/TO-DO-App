package com.example.todolist.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.TaskItem
import com.example.todolist.TaskRepository
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.util.UUID

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    val taskItems = repository.allTasks

    fun addTaskItem(newTask: TaskItem) {
        viewModelScope.launch {
            repository.insert(newTask)
        }
    }

    fun updateTaskItem(id: UUID, name: String, desc: String, dueTime: LocalTime?) {
        viewModelScope.launch {
            val task = taskItems.value?.find { it.id == id }
            if (task != null) {
                task.name = name
                task.desc = desc
                task.dueTime = dueTime
                repository.update(task)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setCompleted(taskItem: TaskItem) {
        viewModelScope.launch {
            if (taskItem.completedDate == null) {
                taskItem.completedDate = java.time.LocalDate.now()
            } else {
                taskItem.completedDate = null
            }
            repository.update(taskItem)
        }
    }

    fun deleteTaskItem(taskItem: TaskItem) {
        viewModelScope.launch {
            repository.delete(taskItem)
        }
    }

}