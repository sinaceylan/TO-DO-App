package com.example.todolist.room

import android.content.Context
import androidx.room.Room

class DatabaseProvider private constructor(context: Context) {
    val database: TaskDatabase

    init {
        database = Room.databaseBuilder(
            context.applicationContext,
            TaskDatabase::class.java, "task-database"
        ).build()
    }

    companion object {
        @Volatile
        private var INSTANCE: DatabaseProvider? = null

        fun getInstance(context: Context): DatabaseProvider {
            return INSTANCE ?: synchronized(this) {
                val instance = DatabaseProvider(context)
                INSTANCE = instance
                instance
            }
        }
    }
}