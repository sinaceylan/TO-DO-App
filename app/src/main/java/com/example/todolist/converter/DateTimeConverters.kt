package com.example.todolist.converter

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalTime

object DateTimeConverters {

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

