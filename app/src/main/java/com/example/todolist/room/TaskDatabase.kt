package com.example.todolist.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.todolist.converter.DateTimeConverters
import com.example.todolist.TaskItem

@Database(entities = [TaskItem::class], version = 2)
@TypeConverters(DateTimeConverters::class)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskItemDao

    companion object {
        private var INSTANCE: TaskDatabase? = null

        fun getInstance(context: Context): TaskDatabase {
            synchronized(this) {
                if (INSTANCE == null) {
                    val migration_1_2 = object : Migration(1, 2) {
                        override fun migrate(database: SupportSQLiteDatabase) {
                            database.execSQL("ALTER TABLE task_table ADD COLUMN dueDate TEXT")
                        }
                    }

                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        TaskDatabase::class.java, "task_database"
                    )
                        .addMigrations(migration_1_2)
                        .build()
                }
                return INSTANCE!!
            }
        }
    }
}