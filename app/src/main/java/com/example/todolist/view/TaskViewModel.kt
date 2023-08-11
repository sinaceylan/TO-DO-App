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

}
/*
class TaskViewModel: ViewModel() {
    var taskItems = MutableLiveData<MutableList<TaskItem>>()

    init {
        taskItems.value = mutableListOf()
    }

    fun addTaskItem(newTask: TaskItem) {
        val list = taskItems.value
        list!!.add(newTask)
        taskItems.postValue(list)
    }

    fun updateTaskItem(id: UUID, name: String, desc: String, dueTime: LocalTime?) {
        val list = taskItems.value
        val task = list!!.find { it.id == id }!!
        task.name = name;
        task.desc = desc;
        task.dueTime = dueTime;
        taskItems.postValue(list)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setCompleted(taskItem: TaskItem) {
        val list = taskItems.value
        val task = list!!.find { it.id == taskItem.id }!!
        if (task.completedDate == null) {
            task.completedDate = java.time.LocalDate.now()
        } else {
            task.completedDate = null
        }

        taskItems.postValue(list)
    }

}

 */