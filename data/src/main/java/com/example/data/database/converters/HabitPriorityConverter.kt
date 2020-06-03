package com.example.data.database.converters

import androidx.room.TypeConverter
import com.example.domain.enums.HabitPriority


class HabitPriorityConverter {
    @TypeConverter
    fun fromHabitType(habitPriority: HabitPriority?): Int? {
        return habitPriority?.value
    }

    @TypeConverter
    fun intToHabitType(value: Int?): HabitPriority? {
        return HabitPriority.getByValue(value)
    }
}