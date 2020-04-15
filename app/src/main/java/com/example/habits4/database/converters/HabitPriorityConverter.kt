package com.example.habits4.database.converters

import androidx.room.TypeConverter
import com.example.habits4.model.enums.HabitPriority

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