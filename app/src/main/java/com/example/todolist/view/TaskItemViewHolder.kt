package com.example.todolist.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.TaskItem
import com.example.todolist.TaskItemClickListener
import com.example.todolist.databinding.TaskItemCellBinding
import java.time.format.DateTimeFormatter

@SuppressLint("NewApi")
class TaskItemViewHolder(
    private val context: Context,
    private val binding: TaskItemCellBinding,
    private val clickListener: TaskItemClickListener

) : RecyclerView.ViewHolder(binding.root) {
    private val timeFormat = DateTimeFormatter.ofPattern("HH:mm")
    private val formattedDate = DateTimeFormatter.ofPattern("yyyy-MM-dd")


    fun bindTaskItem(taskItem: TaskItem) {
        binding.name.text = taskItem.name

        if (taskItem.isCompleted()) {
            binding.name.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            binding.dueTime.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            binding.dueDate.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        }

        if (taskItem.isOverdue()) {
            binding.dueDate.text = "Delayed"
            binding.dueDate.setTextColor(Color.RED)
        } else {
            taskItem.dueDate?.let {
                binding.dueDate.text = formattedDate.format(it)
                binding.dueDate.setTextColor(Color.BLACK)
            } ?: run {
                binding.dueDate.text = "Empty"
                binding.dueDate.setTextColor(Color.GRAY)
            }
        }

        binding.completeButton.setImageResource(taskItem.imageResource())
        binding.completeButton.setColorFilter(taskItem.imageColor(context))

        binding.completeButton.setOnClickListener {
            clickListener.completeTaskItem(taskItem)
        }

        binding.taskCellContainer.setOnClickListener {
            clickListener.editTaskItem(taskItem)
        }

        if (taskItem.dueTime != null) {
            binding.dueTime.text = timeFormat.format(taskItem.dueTime)
        } else {
            binding.dueTime.text = "Empty"
            binding.dueTime.setTextColor(Color.GRAY)
        }

    }
}