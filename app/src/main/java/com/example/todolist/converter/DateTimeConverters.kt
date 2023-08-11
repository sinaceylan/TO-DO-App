package com.example.todolist.converter

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalTime

object DateTimeConverters {

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    @JvmStatic
    fun toLocalDate(value: String?): LocalDate? {
        return value?.let {
            LocalDate.parse(it)
        }
    }

    @TypeConverter
    @JvmStatic
    fun fromLocalDate(date: LocalDate?): String? {
        return date?.toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    @JvmStatic
    fun toLocalTime(value: String?): LocalTime? {
        return value?.let {
            LocalTime.parse(it)
        }
    }

    @TypeConverter
    @JvmStatic
    fun fromLocalTime(time: LocalTime?): String? {
        return time?.toString()
    }
}

