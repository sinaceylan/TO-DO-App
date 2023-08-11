package com.example.todolist.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todolist.TaskItem
import java.util.*

@Dao
interface TaskItemDao {

    @Query("SELECT * FROM task_table")
    fun getAllTasks(): LiveData<List<TaskItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: TaskItem)

    @Update
    suspend fun update(task: TaskItem)

    @Delete
    suspend fun delete(task: TaskItem)

    @Query("DELETE FROM task_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM task_table WHERE id = :taskId")
    fun getTaskById(taskId: UUID): LiveData<TaskItem?>

}