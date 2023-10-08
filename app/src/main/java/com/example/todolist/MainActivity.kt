package com.example.todolist

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.adapter.TaskItemAdapter
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.repository.TaskRepository
import com.example.todolist.view.TaskViewModel
import com.example.todolist.view.TaskViewModelFactory

class MainActivity : AppCompatActivity(), TaskItemClickListener {
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNotificationChannel()

        if (!areNotificationsEnabled()) {
            openNotificationSettings(this)
        }

        val repository = TaskRepository(applicationContext)

        val viewModelFactory = TaskViewModelFactory(repository)
        taskViewModel = ViewModelProvider(this, viewModelFactory).get(TaskViewModel::class.java)

        binding.newTaskButton.setOnClickListener {
            NewTaskSheet(null).show(supportFragmentManager, "newTaskTag")
        }

        setRecyclerView()
    }

    // region RecyclerView

    private fun setRecyclerView() {
        val mainActivity = this

        taskViewModel.taskItems.observe(this) {
            binding.todoListRecyclerView.apply {
                layoutManager = LinearLayoutManager(applicationContext)
                adapter = TaskItemAdapter(it, mainActivity)
            }
        }

        val itemTouchHelperCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    val taskItem =
                        (binding.todoListRecyclerView.adapter as TaskItemAdapter).getItemAt(position)
                    taskViewModel.deleteTaskItem(taskItem)
                }

                override fun onChildDraw(
                    c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                    dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
                ) {
                    val itemView = viewHolder.itemView
                    val icon =
                        ContextCompat.getDrawable(baseContext, R.drawable.ic_baseline_delete_24)
                    val intrinsicWidth = icon!!.intrinsicWidth
                    val intrinsicHeight = icon.intrinsicHeight
                    val color = ContextCompat.getColor(baseContext, R.color.red)

                    val background = RectF(
                        itemView.right.toFloat() + dX,
                        itemView.top.toFloat(),
                        itemView.right.toFloat(),
                        itemView.bottom.toFloat()
                    )

                    val paint = Paint()
                    paint.color = color
                    c.drawRoundRect(background, 0f, 0f, paint)

                    val iconTop = itemView.top + (itemView.height - intrinsicHeight) / 2
                    val iconMargin = (itemView.height - intrinsicHeight) / 2
                    val iconLeft = itemView.right - iconMargin - intrinsicWidth
                    val iconRight = itemView.right - iconMargin
                    val iconBottom = iconTop + intrinsicHeight

                    icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                    icon.draw(c)

                    super.onChildDraw(
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                }
            }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.todoListRecyclerView)

    }

    override fun editTaskItem(taskItem: TaskItem) {
        NewTaskSheet(taskItem).show(supportFragmentManager, "newTaskTag")
    }

    override fun completeTaskItem(taskItem: TaskItem) {
        taskViewModel.setCompleted(taskItem)
    }

    // endregion

    // region Notification Channel

    private fun openNotificationSettings(context: Context) {
        val intent = Intent()
        intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
        intent.putExtra(Settings.EXTRA_CHANNEL_ID, context.applicationInfo.uid)
        context.startActivity(intent)
    }

    private fun setupNotificationChannel() {
        val channelId = "todo_notification_channel"
        val channelName = "Todo Notifications"
        val channelDescription = "Notifications for Todo Tasks"
        val importance = NotificationManager.IMPORTANCE_HIGH

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (notificationManager.getNotificationChannel(channelId) == null) {
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = channelDescription
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun areNotificationsEnabled(): Boolean {
        return NotificationManagerCompat.from(this).areNotificationsEnabled()
    }

    // endregion

}