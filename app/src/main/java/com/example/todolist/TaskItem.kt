package com.example.todolist

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

@Entity(tableName = "task_table")
class TaskItem(
    var name: String,
    var desc: String,
    var dueTime: LocalTime?,
    var completedDate: LocalDate?,
    var dueDate: LocalDate?,
    @PrimaryKey var id: UUID = UUID.randomUUID()
) {

    fun isCompleted(): Boolean {
        return completedDate != null
    }

    fun isOverdue(): Boolean {
        return dueDate != null && dueDate!!.isBefore(LocalDate.now())
    }

    fun imageResource(): Int = if (isCompleted()) R.drawable.checked else R.drawable.unchecked
    fun imageColor(context: Context): Int = if (isCompleted()) green(context) else black(context)

    private fun green(context: Context) = ContextCompat.getColor(context, R.color.green)
    private fun black(context: Context) = ContextCompat.getColor(context, R.color.black)

}