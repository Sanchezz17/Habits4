package com.example.habits4.database.converters

import androidx.room.TypeConverter
import com.example.habits4.model.enums.HabitType

class HabitTypeConverter {
    @TypeConverter
    fun fromHabitType(habitType: HabitType?): Int? {
        return habitType?.value
    }

    @TypeConverter
    fun intToHabitType(value: Int?): HabitType? {
        return HabitType.getByValue(value)
    }
}