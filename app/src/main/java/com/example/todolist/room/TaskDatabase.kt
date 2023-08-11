package com.example.todolist.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todolist.converter.DateTimeConverters
import com.example.todolist.TaskItem

@Database(entities = [TaskItem::class], version = 1)
@TypeConverters(DateTimeConverters::class)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskItemDao
}