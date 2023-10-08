package com.example.todolist

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.databinding.FragmentNewTaskSheetBinding
import com.example.todolist.receiver.NotificationReceiver
import com.example.todolist.view.TaskViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

class NewTaskSheet(var taskItem: TaskItem?) : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentNewTaskSheetBinding
    private lateinit var taskViewModel: TaskViewModel
    private var dueTime: LocalTime? = null
    private var dueDate: LocalDate? = null
    private val editable = Editable.Factory.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI();
        setListeners();
    }

    private fun setupUI() {
        taskItem?.let {
            binding.taskTitle.text = "Edit Task"
            binding.name.text = editable.newEditable(it.name)
            binding.desc.text = editable.newEditable(it.desc)
            binding.deleteButton.visibility = View.VISIBLE
            dueTime = it.dueTime?.also { updateTimeButtonText() }
            dueDate = it.dueDate?.also { updateDateButtonText() }
        } ?: run {
            binding.taskTitle.text = "New Task"
            binding.deleteButton.visibility = View.GONE
        }

        taskViewModel = ViewModelProvider(requireActivity()).get(TaskViewModel::class.java)
    }

    private fun setListeners() {
        binding.apply {
            saveButton.setOnClickListener { saveAction() }
            timePickerButton.setOnClickListener { openTimePicker() }
            deleteButton.setOnClickListener { deleteAction() }
            datePickerButton.setOnClickListener { openDatePicker() }
        }
    }

    private fun deleteAction() {
        taskItem?.let {
            taskViewModel.deleteTaskItem(it)
        }
        dismiss()
    }

    private fun openTimePicker() {
        if (dueTime == null) {
            dueTime = LocalTime.now()
        }

        val listener = TimePickerDialog.OnTimeSetListener { _, selectedHour, selectedMinute ->
            dueTime = LocalTime.of(selectedHour, selectedMinute)
            updateTimeButtonText()

            binding.timePickerButton.setBackgroundColor(resources.getColor(R.color.white))
            binding.timePickerButton.setTextColor(resources.getColor(R.color.black))

        }

        val dialog = TimePickerDialog(
            activity,
            listener,
            dueTime!!.hour,
            dueTime!!.minute,
            true
        )

        dialog.setTitle("Task Due")
        dialog.show()
    }

    private fun updateTimeButtonText() {
        if (dueTime != null) {
            binding.timePickerButton.text =
                String.format("%02d:%02d", dueTime!!.hour, dueTime!!.minute)
            binding.timePickerButton.setBackgroundColor(resources.getColor(R.color.white))
            binding.timePickerButton.setTextColor(resources.getColor(R.color.black))
        }
    }

    private fun updateDateButtonText() {
        if (dueDate != null) {
            binding.datePickerButton.text =
                dueDate?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            binding.datePickerButton.setBackgroundColor(resources.getColor(R.color.white))
            binding.datePickerButton.setTextColor(resources.getColor(R.color.black))
        }
    }

    private fun openDatePicker() {
        val currentDate = LocalDate.now()
        val year = currentDate.year
        val month = currentDate.monthValue - 1
        val day = currentDate.dayOfMonth

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                dueDate = LocalDate.of(selectedYear, selectedMonth + 1, selectedDay)
                Log.d("NewTaskSheet", "Selected Due Date: $dueDate")
                updateDateButtonText()

                this@NewTaskSheet.dueDate = dueDate

                // Reset button colors when a new date is set
                binding.datePickerButton.setBackgroundColor(resources.getColor(R.color.white))
                binding.datePickerButton.setTextColor(resources.getColor(R.color.black))
            },
            year, month, day
        )

        datePickerDialog.setTitle("Select Due Date")
        datePickerDialog.show()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = FragmentNewTaskSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun saveAction() {
        val name = binding.name.text.toString()
        val desc = binding.desc.text.toString()

        if (taskItem == null) {
            val newTask = TaskItem(name, desc, dueTime, null, dueDate)
            taskViewModel.addTaskItem(newTask)
        } else {
            taskViewModel.updateTaskItem(taskItem!!.id, name, desc, dueTime)
        }

        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!alarmManager.canScheduleExactAlarms()) {
                val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                    data = Uri.fromParts("package", requireContext().packageName, null)
                }
                startActivityForResult(intent, REQUEST_CODE_EXACT_ALARM_PERMISSION)
                return
            }
        }

        if (dueDate != null && dueTime != null) {
            val calendar = Calendar.getInstance().apply {
                set(Calendar.YEAR, dueDate!!.year)
                set(Calendar.MONTH, dueDate!!.monthValue - 1) // Month is 0-indexed
                set(Calendar.DAY_OF_MONTH, dueDate!!.dayOfMonth)
                set(Calendar.HOUR_OF_DAY, dueTime!!.hour)
                set(Calendar.MINUTE, dueTime!!.minute)
                set(Calendar.SECOND, 0)
            }

            val oneHourBefore = calendar.timeInMillis - 3600000 // 1 hour in milliseconds
            val thirtyMinBefore = calendar.timeInMillis - 1800000 // 30 min in milliseconds

            val intentOneHour = Intent(context, NotificationReceiver::class.java).apply {
                putExtra("title", name)
                putExtra("desc", "$desc - 1 hour left")
            }

            val intentThirtyMin = Intent(context, NotificationReceiver::class.java).apply {
                putExtra("title", name)
                putExtra("desc", "$desc - 30 minutes left")
            }

            val pendingIntentOneHour = PendingIntent.getBroadcast(
                context,
                1,
                intentOneHour,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            val pendingIntentThirtyMin = PendingIntent.getBroadcast(
                context,
                2,
                intentThirtyMin,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            if (oneHourBefore > System.currentTimeMillis()) { // Check if the time is in the future
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, oneHourBefore, pendingIntentOneHour)
            }

            if (thirtyMinBefore > System.currentTimeMillis()) // Check if the time is in the future
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    thirtyMinBefore,
                    pendingIntentThirtyMin
                )

            // For the actual task time
            val intent = Intent(context, NotificationReceiver::class.java).apply {
                putExtra("title", name)
                putExtra("desc", desc)
            }

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

        }

        binding.name.setText("")
        binding.desc.setText("")
        dismiss()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_EXACT_ALARM_PERMISSION) {
            saveAction()
        }
    }

    companion object {
        private const val REQUEST_CODE_EXACT_ALARM_PERMISSION = 1001
    }

}